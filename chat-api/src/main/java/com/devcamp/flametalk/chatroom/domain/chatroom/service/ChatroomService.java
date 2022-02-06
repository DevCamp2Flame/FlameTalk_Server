package com.devcamp.flametalk.chatroom.domain.chatroom.service;

import static com.devcamp.flametalk.chatroom.global.error.ErrorCode.HOST_USER_CONFLICT_EXCEPTION;
import static com.devcamp.flametalk.chatroom.global.error.ErrorCode.USER_NOT_FOUND;

import com.devcamp.flametalk.chatroom.domain.chatroom.domain.Chatroom;
import com.devcamp.flametalk.chatroom.domain.chatroom.domain.ChatroomRepository;
import com.devcamp.flametalk.chatroom.domain.chatroom.domain.UserChatroom;
import com.devcamp.flametalk.chatroom.domain.chatroom.domain.UserChatroomRepository;
import com.devcamp.flametalk.chatroom.domain.chatroom.dto.ChatroomCreateRequest;
import com.devcamp.flametalk.chatroom.domain.chatroom.dto.ChatroomCreateResponse;
import com.devcamp.flametalk.chatroom.domain.friend.domain.FriendRepository;
import com.devcamp.flametalk.chatroom.domain.profile.domain.ProfileRepository;
import com.devcamp.flametalk.chatroom.domain.user.domain.User;
import com.devcamp.flametalk.chatroom.domain.user.domain.UserRepository;
import com.devcamp.flametalk.chatroom.global.error.exception.ConflictException;
import com.devcamp.flametalk.chatroom.global.error.exception.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChatroomService {

  private final ChatroomRepository chatroomRepository;
  private final UserChatroomRepository userChatroomRepository;
  private final UserRepository userRepository;
  private final FriendRepository friendRepository;
  private final ProfileRepository profileRepository;

  // 채팅방 생성
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
//        Profile profile = Optional.ofNullable(
//                friendRepository.findByUserAndFriendUser(user.get(), hostUser))
//            .map(Friend::getAssignedProfile)
//            .orElse(profileRepository.findByUserAndIsDefault(user.get(), Boolean.TRUE));

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
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));
    return user;
  }
}
