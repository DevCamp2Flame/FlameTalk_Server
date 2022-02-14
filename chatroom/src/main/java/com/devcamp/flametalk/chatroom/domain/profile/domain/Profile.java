package com.devcamp.flametalk.chatroom.domain.profile.domain;

import com.devcamp.flametalk.chatroom.domain.friend.domain.Friend;
import com.devcamp.flametalk.chatroom.domain.model.BaseTime;
import com.devcamp.flametalk.chatroom.domain.user.domain.User;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Profile 엔티티 입니다.
 */
@Getter
@NoArgsConstructor
@Entity
public class Profile extends BaseTime {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String imageUrl;

  private String bgImageUrl;

  private String description;

  @Convert(converter = StickerConverter.class)
  private List<Sticker> sticker;

  @Column(nullable = false, columnDefinition = "TINYINT", length = 1)
  @NotNull
  private Boolean isDefault;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @OneToMany(mappedBy = "assignedProfile")
  private List<Friend> assignedProfiles = new ArrayList<>();

  /**
   * Profile 엔티티의 빌더입니다.
   *
   * @param id          프로필 id
   * @param imageUrl    프로필 사진 S3 URL
   * @param bgImageUrl  프로필 배경 사진 S3 URL
   * @param sticker     프로필에 적용된 sticker
   * @param description 프로필 상태 메세지
   * @param isDefault   기본 프로필 여부
   * @param user        프로필에 해당하는 유저
   */
  @Builder
  public Profile(long id, String imageUrl, String bgImageUrl, List<Sticker> sticker,
      String description, boolean isDefault, User user) {
    this.id = id;
    this.imageUrl = imageUrl;
    this.bgImageUrl = bgImageUrl;
    this.sticker = sticker;
    this.description = description;
    this.isDefault = isDefault;
    this.user = user;
  }

  /**
   * 프로필 엔티티를 업데이트합니다.
   *
   * @param updatedProfile 업데이트될 프로필
   * @return 업데이트된 프로필
   */
  public Profile update(Profile updatedProfile) {
    this.imageUrl = updatedProfile.imageUrl;
    this.bgImageUrl = updatedProfile.bgImageUrl;
    this.sticker = updatedProfile.sticker;
    this.description = updatedProfile.description;
    this.isDefault = updatedProfile.isDefault;
    this.user = updatedProfile.user;

    return this;
  }
}