package com.devcamp.flametalk.chatroom.domain.chatroom.service;

import static com.devcamp.flametalk.chatroom.global.error.ErrorCode.CHATROOM_NOT_FOUND;
import static com.devcamp.flametalk.chatroom.global.error.ErrorCode.HOST_USER_CONFLICT_EXCEPTION;
import static com.devcamp.flametalk.chatroom.global.error.ErrorCode.USER_CHATROOM_NOT_FOUND;
import static com.devcamp.flametalk.chatroom.global.error.ErrorCode.USER_NOT_FOUND;

import com.devcamp.flametalk.chatroom.domain.chatroom.domain.Chatroom;
import com.devcamp.flametalk.chatroom.domain.chatroom.domain.ChatroomRepository;
import com.devcamp.flametalk.chatroom.domain.chatroom.domain.UserChatroom;
import com.devcamp.flametalk.chatroom.domain.chatroom.domain.UserChatroomRepository;
import com.devcamp.flametalk.chatroom.domain.chatroom.dto.ChatroomCreateRequest;
import com.devcamp.flametalk.chatroom.domain.chatroom.dto.ChatroomCreateResponse;
import com.devcamp.flametalk.chatroom.domain.chatroom.dto.ChatroomFilesResponse;
import com.devcamp.flametalk.chatroom.domain.chatroom.dto.ChatroomsResponse;
import com.devcamp.flametalk.chatroom.domain.chatroom.dto.JoinChatroomRequest;
import com.devcamp.flametalk.chatroom.domain.chatroom.dto.JoinChatroomResponse;
import com.devcamp.flametalk.chatroom.domain.chatroom.dto.ProfileSimpleResponse;
import com.devcamp.flametalk.chatroom.domain.chatroom.dto.UserChatroomCloseRequest;
import com.devcamp.flametalk.chatroom.domain.chatroom.dto.UserChatroomDetailResponse;
import com.devcamp.flametalk.chatroom.domain.chatroom.dto.UserChatroomResponse;
import com.devcamp.flametalk.chatroom.domain.chatroom.dto.UserChatroomSimpleResponse;
import com.devcamp.flametalk.chatroom.domain.chatroom.dto.UserChatroomUpdateRequest;
import com.devcamp.flametalk.chatroom.domain.file.domain.File;
import com.devcamp.flametalk.chatroom.domain.file.domain.FileRepository;
import com.devcamp.flametalk.chatroom.domain.profile.domain.Profile;
import com.devcamp.flametalk.chatroom.domain.profile.domain.ProfileRepository;
import com.devcamp.flametalk.chatroom.domain.user.domain.User;
import com.devcamp.flametalk.chatroom.domain.user.domain.UserRepository;
import com.devcamp.flametalk.chatroom.global.error.exception.ConflictException;
import com.devcamp.flametalk.chatroom.global.error.exception.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 채팅방 관련 API 로직을 관리하는 Service 입니다.
 */
@RequiredArgsConstructor
@Service
public class ChatroomService {

  private final ChatroomRepository chatroomRepository;
  private final UserChatroomRepository userChatroomRepository;
  private final UserRepository userRepository;
  private final ProfileRepository profileRepository;
  private final FileRepository fileRepository;

  /**
   * 채팅방을 DB에 생성합니다. 채팅방에 초대된 유저들의 userChatroom 까지 DB에 생성됩니다.
   *
   * @param userId  채팅방 개설자
   * @param request 생성할 채팅방 정보
   * @return 생성된 채팅방 기본 정보
   */
  @Transactional
  public ChatroomCreateResponse save(String userId, ChatroomCreateRequest request) {
    User hostUser = validateUser(userId, request.getHostId());

    if (request.getIsOpen()) { // 오픈 채팅방의 경우
      // TODO: 오픈 채팅방 생성
      // url 생성 -> Chatroom 생성
      // 개설자의 UserChatroom만 생성
    } else { // 일반 채팅방의 경우
      // Chatroom 생성
      Chatroom savedChatroom = chatroomRepository.save(request.toChatroom(null));

      // 채팅방에 초대된 유저들에 대해 UserChatroom 생성
      List<UserChatroom> userChatrooms = new ArrayList<>();
      List<String> defaultTitle = new ArrayList<>();
      List<String> defaultThumbnail = new ArrayList<>();
      for (String chatroomUserId : request.getUsers()) {
        Optional<User> user = userRepository.findById(chatroomUserId);
        if (user.isEmpty()) {
          continue;
        }

        UserChatroom userChatroom = UserChatroom.builder()
            .chatroom(savedChatroom)
            .user(user.get())
            .title(request.getTitle())
            .imageUrl(request.getThumbnail())
            .inputLock(false)
            .build();
        userChatrooms.add(userChatroom);

        defaultTitle.add(user.get().getNickname());

        // TODO: [FRIEND SERVER] 친구가 나에게 보여주는 프로필이 기본인지 멀티 프로필인지 확인

        defaultThumbnail.add(
            profileRepository.findByUserAndIsDefault(user.get(), Boolean.TRUE).getImageUrl());
      }
      userChatroomRepository.saveAll(userChatrooms);

      ChatroomCreateResponse response = ChatroomCreateResponse.of(
          userChatroomRepository.findByUserAndChatroom(hostUser, savedChatroom), savedChatroom);

      // TODO: refactor
      if (response.getThumbnail() == null) { // 채팅방 사진을 지정하지 않은 경우 유저들의 프로필로 생성
        response.updateDefaultThumbnail(defaultThumbnail);
      }
      if (response.getTitle() == null) { // 채팅방 이름을 지정하지 않은 경우 유저들의 닉네임으로 생성
        response.updateDefaultTitle(String.join(", ", defaultTitle));
      }

      return response;
    }
    return null;
  }

  private User validateUser(String userId, String hostId) {
    if (!userId.equals(hostId)) {
      throw new ConflictException(HOST_USER_CONFLICT_EXCEPTION);
    }
    return userRepository.findById(userId)
        .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));
  }

  /**
   * 기존 채팅방에 유저가 입장함에 따라 UserChatroom 엔티티가 생성되며 채팅방 인원수가 증가합니다.
   *
   * @param request 입장 정보
   * @return 입장한 채팅방 정보
   */
  @Transactional
  public JoinChatroomResponse joinChatroom(JoinChatroomRequest request) {
    // TODO: 오픈 채팅방, 멀티프로필
    Chatroom chatroom = chatroomRepository.findById(request.getChatroomId())
        .orElseThrow(() -> new EntityNotFoundException(CHATROOM_NOT_FOUND));
    User user = userRepository.findById(request.getUserId())
        .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));

    UserChatroom userChatroom = request.toUserChatroom(chatroom, user);
    UserChatroom saved = userChatroomRepository.save(userChatroom);
    chatroom.join();

    return JoinChatroomResponse
        .of(chatroom, saved, makeDefaultChatroomTitle(chatroom, userChatroom),
            makeDefaultChatroomThumbnail(chatroom, userChatroom));
  }

  /**
   * id에 해당하는 유저의 채팅방 상세 정보를 조회합니다.
   *
   * @param id 유저 채팅방 id
   * @return 조회된 유저 채팅방 상세 정보
   */
  @Transactional
  public UserChatroomDetailResponse findByUserChatroomId(Long id) {
    UserChatroom userChatroom = userChatroomRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(USER_CHATROOM_NOT_FOUND));
    Profile userProfile = profileRepository.findByUserAndIsDefault(userChatroom.getUser(), true);

    // TODO: 멀티프로필(친구 서버) 적용
    // TODO: 오픈 채팅방의 경우 고려
    List<UserChatroom> friendChatrooms = userChatroomRepository.findAllByChatroom(
        userChatroom.getChatroom());
    List<Profile> friendProfiles = new ArrayList<>();
    friendChatrooms.forEach(friendChatroom -> {
      User user = friendChatroom.getUser();
      if (user.getId().equals(userChatroom.getUser().getId())) {
        return;
      }
      Profile friendProfile = profileRepository.findByUserAndIsDefault(user, true);
      friendProfiles.add(friendProfile);
    });

    List<File> files = userChatroom.getChatroom().getFiles();
    List<String> fileUrls = new ArrayList<>();
    files.forEach(file -> {
      if (fileUrls.size() > 4) {
        return;
      }
      fileUrls.add(file.getUrl());
    });

    return UserChatroomDetailResponse.of(userChatroom, userProfile,
        ProfileSimpleResponse.createList(friendProfiles), fileUrls);
  }

  /**
   * 유저가 참여중인 채팅방 리스트를 조회합니다.
   *
   * @param userId 유저 id
   * @param isOpen 오픈 채팅방 여부
   * @return 유저가 참여중인 채팅방 일부 정보에 대한 리스트
   */
  public ChatroomsResponse findAllByUserId(String userId, boolean isOpen) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));
    List<UserChatroom> userChatrooms = userChatroomRepository.findAllByUser(user);
    List<UserChatroomResponse> userChatroomResponses = new ArrayList<>();

    userChatrooms.forEach(userChatroom -> {
      Chatroom chatroom = userChatroom.getChatroom();
      if ((chatroom.getIsOpen() && isOpen) || (!chatroom.getIsOpen() && !isOpen)) {
        UserChatroomResponse response = UserChatroomResponse.of(userChatroom);
        if (response.getThumbnail() == null) { // 채팅방 사진을 지정하지 않은 경우 유저들의 프로필로 생성
          response.updateDefaultThumbnail(
              makeDefaultChatroomThumbnail(userChatroom.getChatroom(), userChatroom));
        }
        if (response.getTitle() == null) { // 채팅방 이름을 지정하지 않은 경우 유저들의 닉네임으로 생성
          response.updateDefaultTitle(
              makeDefaultChatroomTitle(userChatroom.getChatroom(), userChatroom));
        }
        userChatroomResponses.add(response);
      }
    });

    return ChatroomsResponse.of(userId, userChatroomResponses);
  }

  /**
   * 채팅방에 업르드된 파일 객체를 모두 조회합니다.
   *
   * @param id 채팅방 id
   * @return 생성 시간 역순으로 정렬된 파일 정보 리스트
   */
  public List<ChatroomFilesResponse> findAllFilesByChatroomId(String id) {
    // TODO: 파일 서버로 로직 분리 or 파일 서버 호출
    Chatroom chatroom = chatroomRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(CHATROOM_NOT_FOUND));

    List<File> chatroomFiles = fileRepository.findAllByChatroom(chatroom);
    List<ChatroomFilesResponse> response = new ArrayList<>();
    chatroomFiles.forEach(file ->
        response.add(new ChatroomFilesResponse(file.getId(), file.getUrl(), file.getTitle(),
            file.getExtension(), file.getCreatedAt())));
    Collections.sort(response);
    return response;
  }

  /**
   * DB에 저장된 유저 채팅방 정보를 업데이트합니다.
   *
   * @param id      유저 채팅방 id
   * @param request 업데이트할 유저 채팅방 정보
   * @return 업데이트된 유저 채팅방 정보
   */
  @Transactional
  public UserChatroomSimpleResponse updateUserChatroom(Long id, UserChatroomUpdateRequest request) {
    UserChatroom userChatroom = userChatroomRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(USER_CHATROOM_NOT_FOUND));

    UserChatroom requestUserChatroom = request.toUserChatroom(userChatroom);
    UserChatroom updatedUserChatroom = userChatroom.update(requestUserChatroom);

    UserChatroomSimpleResponse response = UserChatroomSimpleResponse.from(updatedUserChatroom);

    if (response.getThumbnail() == null) { // 채팅방 사진을 지정하지 않은 경우 유저들의 프로필로 생성
      response.updateDefaultThumbnail(
          makeDefaultChatroomThumbnail(userChatroom.getChatroom(), userChatroom));
    }
    if (response.getTitle() == null) { // 채팅방 이름을 지정하지 않은 경우 유저들의 닉네임으로 생성
      response
          .updateDefaultTitle(makeDefaultChatroomTitle(userChatroom.getChatroom(), userChatroom));
    }
    return response;
  }

  private String makeDefaultChatroomTitle(Chatroom chatroom, UserChatroom user) {
    List<UserChatroom> userChatrooms = userChatroomRepository.findAllByChatroom(chatroom);
    List<String> nicknames = new ArrayList<>();
    userChatrooms.forEach(userChatroom -> {
      if (userChatroom.getId().equals(user.getId())) {
        return;
      }
      nicknames.add(userChatroom.getUser().getNickname());
    });
    return String.join(", ", nicknames);
  }

  private List<String> makeDefaultChatroomThumbnail(Chatroom chatroom, UserChatroom user) {
    List<UserChatroom> userChatrooms = userChatroomRepository.findAllByChatroom(chatroom);
    List<String> imageUrls = new ArrayList<>();
    for (UserChatroom userChatroom : userChatrooms) {
      if (imageUrls.size() > 4) {
        break;
      }
      if (!userChatroom.getId().equals(user.getId())) {
        imageUrls.add(
            profileRepository.findByUserAndIsDefault(userChatroom.getUser(), true).getImageUrl());
      }
    }
    return imageUrls;
  }

  /**
   * DB에 저장된 유저 채팅방의 마지막 확인 메세지 값을 업데이트합니다.
   *
   * @param request 업데이트할 유저 채팅방 정보
   */
  @Transactional
  public void closeUserChatroom(UserChatroomCloseRequest request) {
    UserChatroom userChatroom = userChatroomRepository.findById(request.getUserChatroomId())
        .orElseThrow(() -> new EntityNotFoundException(USER_CHATROOM_NOT_FOUND));
    userChatroom.close(request.getLastReadMessageId());
  }

  /**
   * DB에 저장된 유저 채팅방을 삭제합니다. 해당 이벤트로 채팅방의 인원이 0명이라면 채팅방도 삭제합니다.
   *
   * @param id 유저 채팅방 id
   */
  @Transactional
  public void deleteUserChatroomById(Long id) {
    UserChatroom userChatroom = userChatroomRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(USER_CHATROOM_NOT_FOUND));
    userChatroomRepository.delete(userChatroom);

    Chatroom chatroom = userChatroom.getChatroom();
    chatroom.leave();
    if (chatroom.getCount() == 0) {
      chatroomRepository.delete(chatroom);
    }
  }
}
