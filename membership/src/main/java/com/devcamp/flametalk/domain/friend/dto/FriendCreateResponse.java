package com.devcamp.flametalk.domain.friend.dto;

import com.devcamp.flametalk.domain.friend.domain.Friend;
import com.devcamp.flametalk.domain.friend.domain.Preview;
import com.devcamp.flametalk.domain.profile.domain.Profile;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 친구 추가 응답을 위한 클래스입니다.
 */
@AllArgsConstructor
@Getter
public class FriendCreateResponse {

  private Long friendId;
  private String friendNickname;
  private Long profileId;
  private String imageUrl;
  private String description;

  /**
   * 유저 혼자 친구 추가한 경우, Friend 엔티티와 Profile 엔티티로부터 응답 객체를 생성하는 정적 팩토리 메소드입니다.
   *
   * @param friend        친구 관계 Entity
   * @param friendProfile 추가한 친구의 기본 프로필 Entity
   * @return 생성된 친구 관계 응답 정보
   */
  public static FriendCreateResponse ofOneSide(Friend friend, Profile friendProfile) {
    return new FriendCreateResponse(
        friend.getId(),
        friend.getUserFriend().getNickname(),
        friendProfile.getId(),
        friendProfile.getImageUrl(),
        friendProfile.getDescription()
    );
  }

  /**
   * 상대방이 나를 친구로 추가하고 나도 친구로 추가한 경우, 나의 Friend 관계 정보와 친구의 Friend 관계 정보를 바탕으로 응답 객체를 생성하는 정적 팩토리
   * 메소드입니다.
   *
   * @param friend             유저의 친구 관계 Entity
   * @param userFriendRelation 추가하는 친구 유저의 친구관계 Entity
   * @return 생성된 친구 관계 응답 정보
   */
  public static FriendCreateResponse of(Friend friend, Preview preview) {
    return new FriendCreateResponse(
        friend.getId(),
        friend.getUserFriend().getNickname(),
        preview.getProfileId(),
        preview.getImageUrl(),
        preview.getDescription()
    );
  }
}
