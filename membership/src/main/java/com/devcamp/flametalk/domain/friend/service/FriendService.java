package com.devcamp.flametalk.domain.friend.service;

import com.devcamp.flametalk.domain.friend.domain.Friend;
import com.devcamp.flametalk.domain.friend.domain.FriendRepository;
import com.devcamp.flametalk.domain.friend.domain.Preview;
import com.devcamp.flametalk.domain.friend.dto.FriendCreateRequest;
import com.devcamp.flametalk.domain.friend.dto.FriendCreateResponse;
import com.devcamp.flametalk.domain.friend.dto.FriendUpdateRequest;
import com.devcamp.flametalk.domain.friend.dto.FriendUpdateResponse;
import com.devcamp.flametalk.domain.friend.dto.FriendsCreateRequest;
import com.devcamp.flametalk.domain.profile.domain.Profile;
import com.devcamp.flametalk.domain.profile.domain.ProfileRepository;
import com.devcamp.flametalk.domain.user.domain.User;
import com.devcamp.flametalk.domain.user.domain.UserRepository;
import com.devcamp.flametalk.global.error.ErrorCode;
import com.devcamp.flametalk.global.error.exception.EntityExistsException;
import com.devcamp.flametalk.global.error.exception.EntityNotFoundException;
import com.devcamp.flametalk.global.error.exception.ForbiddenException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Friend API 로직을 관리하는 Service 입니다.
 */
@RequiredArgsConstructor
@Service
public class FriendService {

  private final FriendRepository friendRepository;
  private final UserRepository userRepository;
  private final ProfileRepository profileRepository;

  /**
   * 친구 관계를 모두 DB에 생성합니다. 생성된 모든 친구들에게 나의 기본 프로필을 보여줍니다. 핸드폰 번호가 등록되지 않은 유저나 이미 친구로 등록된 유저는 친구로 생성하지
   * 않습니다.
   *
   * @param userId  친구를 추가하는 유저의 id
   * @param request 친구로 등록할 유저의 핸드폰 번호 리스트
   */
  @Transactional
  public void saveAll(String userId, FriendsCreateRequest request) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND));
    Profile userDefaultProfile = profileRepository.findByUserAndIsDefault(user, true)
        .orElseThrow(() -> new EntityNotFoundException(ErrorCode.DEFAULT_PROFILE_NOT_FOUND));
    Preview userDefaultProfilePreview = Preview.from(userDefaultProfile);

    List<Friend> friends = new ArrayList<>();
    request.getPhoneNumbers().forEach(phoneNumber -> {
      User friendUser = userRepository.findByPhoneNumber(phoneNumber).orElse(null);
      if (friendUser == null || friendRepository.existsByUserAndUserFriend(user, friendUser)) {
        return;
      }

      FriendCreateRequest friendCreateRequest = new FriendCreateRequest(userDefaultProfile.getId(),
          phoneNumber);
      Friend friend = friendCreateRequest
          .toFriend(user, friendUser, userDefaultProfile, userDefaultProfilePreview);
      friends.add(friend);
    });

    friendRepository.saveAll(friends);
  }

  /**
   * 친구 관계를 DB에 생성합니다.
   *
   * @param userId  친구를 추가하는 유저의 id
   * @param request 추가할 친구 요청 정보
   * @return 추가된 친구 관계와 친구 프로필 정보
   */
  @Transactional
  public FriendCreateResponse save(String userId, FriendCreateRequest request) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND));

    // TODO: refactor
    User userFriend = userRepository.findByPhoneNumber(request.getPhoneNumber())
        .orElse(null);
    if (userFriend == null) {
      return null;
    }

    Profile assignedProfile = profileRepository.findById(request.getProfileId())
        .orElseThrow(() -> new EntityNotFoundException(ErrorCode.PROFILE_NOT_FOUND));
    if (friendRepository.existsByUserAndUserFriend(user, userFriend)) {
      throw new EntityExistsException(ErrorCode.FRIEND_EXIST);
    }

    Friend friend = request
        .toFriend(user, userFriend, assignedProfile, Preview.from(assignedProfile));
    friendRepository.save(friend);

    Friend userFriendRelation = friendRepository
        .findByUserAndUserFriend(userFriend, user).orElse(null);

    if (userFriendRelation == null) {
      Profile friendDefaultProfile = profileRepository.findByUserAndIsDefault(userFriend, true)
          .orElseThrow(() -> new EntityNotFoundException(ErrorCode.DEFAULT_PROFILE_NOT_FOUND));
      return FriendCreateResponse.ofOneSide(friend, friendDefaultProfile);
    }

    return FriendCreateResponse.of(friend, userFriendRelation);
  }

  /**
   * DB에 저장된 친구 관계 정보를 업데이트합니다.
   *
   * @param friendId 업데이트할 친구 관계 id
   * @param request  업데이트할 친구 관계 데이터
   * @return 업데이트된 친구 관계 정보
   */
  @Transactional
  public FriendUpdateResponse update(Long friendId, FriendUpdateRequest request) {
    Friend friend = friendRepository.findById(friendId)
        .orElseThrow(() -> new EntityNotFoundException(ErrorCode.FRIEND_NOT_FOUND));
    Profile profile = profileRepository.findById(request.getAssignedProfileId())
        .orElseThrow(() -> new EntityNotFoundException(ErrorCode.PROFILE_NOT_FOUND));
    if (!profile.getUser().getId().equals(friend.getUser().getId())) {
      throw new ForbiddenException(ErrorCode.FORBIDDEN_PROFILE);
    }

    Friend requestFriend = request.toFriend(friend, profile);
    Friend updatedFriend = friend.update(requestFriend);
    return FriendUpdateResponse.of(updatedFriend, getFriendProfile(friend));
  }

  private Preview getFriendProfile(Friend friend) {
    User user = friend.getUser();
    User userFriend = friend.getUserFriend();
    Friend userFriendRelation = friendRepository.findByUserAndUserFriend(userFriend, user)
        .orElse(null);

    if (userFriendRelation == null) {
      Profile friendDefaultProfile = profileRepository.findByUserAndIsDefault(userFriend, true)
          .orElseThrow(() -> new EntityNotFoundException(ErrorCode.DEFAULT_PROFILE_NOT_FOUND));
      return Preview.from(friendDefaultProfile);
    }
    return userFriendRelation.getPreview();
  }
}
