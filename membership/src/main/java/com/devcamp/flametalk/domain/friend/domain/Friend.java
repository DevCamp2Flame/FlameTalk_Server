package com.devcamp.flametalk.domain.friend.domain;

import com.devcamp.flametalk.domain.model.BaseTime;
import com.devcamp.flametalk.domain.profile.domain.Profile;
import com.devcamp.flametalk.domain.user.domain.User;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Friend 엔티티 입니다.
 */
@Getter
@NoArgsConstructor
@Entity
public class Friend extends BaseTime {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Convert(converter = PreviewConverter.class)
  private Preview preview;

  @NotNull
  private Boolean isMarked;

  @NotNull
  private Boolean isHidden;

  @NotNull
  private Boolean isBlocked;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne
  @JoinColumn(name = "friend_id")
  private User userFriend;

  @ManyToOne
  @JoinColumn(name = "assigned_profile_id")
  private Profile profile;

  /**
   * Friend 엔티티의 빌더입니다.
   *
   * @param id         친구 관계 id
   * @param preview    친구에게 보여줄 유저의 프로필 프리뷰
   * @param isMarked   관심 친구 여부
   * @param isHidden   숨김 친구 여부
   * @param isBlocked  차단 친구 여부
   * @param user       친구 추가를 요청한 유저
   * @param userFriend 추가되는 친구에 해당하는 유저
   * @param profile    유저가 친구에게 보여줄 프로필
   */
  @Builder
  public Friend(Long id, Preview preview, boolean isMarked, boolean isHidden, boolean isBlocked,
      User user, User userFriend, Profile profile) {
    this.id = id;
    this.preview = preview;
    this.isMarked = isMarked;
    this.isHidden = isHidden;
    this.isBlocked = isBlocked;
    this.user = user;
    this.userFriend = userFriend;
    this.profile = profile;
  }

  /**
   * 친구 엔티티를 업데이트합니다.
   *
   * @param updatedFriend 업데이트 될 친구 관계 정보
   * @return 업데이트된 친구 관계
   */
  public Friend update(Friend updatedFriend) {
    this.preview = updatedFriend.preview;
    this.isMarked = updatedFriend.isMarked;
    this.isHidden = updatedFriend.isHidden;
    this.isBlocked = updatedFriend.isBlocked;
    this.profile = updatedFriend.profile;
    return this;
  }

  public void updatePreview(Preview updatedPreview) {
    this.preview = updatedPreview;
  }
}
