package com.devcamp.flametalk.domain.friend.dto;

import com.devcamp.flametalk.domain.friend.domain.Friend;
import com.devcamp.flametalk.domain.friend.domain.Preview;
import com.devcamp.flametalk.domain.profile.domain.Profile;
import com.devcamp.flametalk.domain.user.domain.User;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 친구 한명 추가 요청을 위한 클래스입니다.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class FriendCreateRequest {

  @NotNull
  private Long profileId;

  @NotNull
  @Size(min = 13, max = 13)
  private String phoneNumber;

  /**
   * 인스턴스 정보를 Friend 엔티티로 반환하는 메소드입니다.
   *
   * @param user       친구를 추가하고자하는 유저
   * @param userFriend 친구 추가 대상 유저
   * @param profile    유저가 추가하는 친구에게 보여줄 유저의 프로필
   * @param preview    유저가 추가하는 친구에게 보여줄 유저의 프로필의 프리뷰
   * @return Friend 엔티티
   */
  public Friend toFriend(User user, User userFriend, Profile profile, Preview preview) {
    return Friend.builder()
        .user(user)
        .userFriend(userFriend)
        .profile(profile)
        .preview(preview)
        .isMarked(false)
        .isHidden(false)
        .isBlocked(false)
        .build();
  }
}
