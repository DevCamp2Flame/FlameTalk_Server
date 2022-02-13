package com.devcamp.flametalk.domain.friend.dto;


import com.devcamp.flametalk.domain.friend.domain.Friend;
import com.devcamp.flametalk.domain.friend.domain.Preview;
import com.devcamp.flametalk.domain.profile.domain.Profile;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 친구 관계 수정을 위한 클래스입니다.
 */
@AllArgsConstructor
@Getter
public class FriendUpdateRequest {

  @NotNull
  public Long assignedProfileId;

  @NotNull
  public boolean isMarked;

  @NotNull
  public boolean isHidden;

  @NotNull
  public boolean isBlocked;

  /**
   * 인스턴스 정보를 업데이트할 Friend 엔티티로 반환하는 메소드입니다.
   *
   * @param friend  기존 Friend 엔티티
   * @param profile 친구에게 새로 할당할 프로필
   * @return 업데이트된 Friend 엔티티
   */
  public Friend toFriend(Friend friend, Profile profile) {
    return Friend.builder()
        .id(friend.getId())
        .preview(Preview.from(profile))
        .isMarked(isMarked)
        .isHidden(isHidden)
        .isBlocked(isBlocked)
        .user(friend.getUser())
        .userFriend(friend.getUserFriend())
        .profile(profile)
        .build();
  }
}
