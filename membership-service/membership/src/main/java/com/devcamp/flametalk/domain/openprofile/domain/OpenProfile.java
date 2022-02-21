package com.devcamp.flametalk.domain.openprofile.domain;

import com.devcamp.flametalk.domain.model.BaseTime;
import com.devcamp.flametalk.domain.user.domain.User;
import javax.persistence.Column;
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
 * OpenProfile 엔티티 입니다.
 */
@Getter
@NoArgsConstructor
@Entity
public class OpenProfile extends BaseTime {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @Column(length = 20)
  private String nickname;

  private String imageUrl;

  @Column(length = 60)
  private String description;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User openProfileUser;

  /**
   * OpenProfile 엔티티의 빌더입니다.
   *
   * @param id          오픈 프로필 id
   * @param nickname    오픈 프로필 닉네임
   * @param imageUrl    오픈 프로필 사진 S3 URL
   * @param description 오픈 프로필 상태 메세지
   * @param user        오픈 프로필에 해당하는 유저
   */
  @Builder
  public OpenProfile(Long id, String nickname, String imageUrl, String description, User user) {
    this.id = id;
    this.nickname = nickname;
    this.imageUrl = imageUrl;
    this.description = description;
    this.openProfileUser = user;
  }

  /**
   * 오픈 프로필 엔티티를 업데이트합니다.
   *
   * @param updatedOpenProfile 업데이트될 오픈 프로필
   * @return 업데이트된 오픈 프로필
   */
  public OpenProfile update(OpenProfile updatedOpenProfile) {
    this.nickname = updatedOpenProfile.nickname;
    this.imageUrl = updatedOpenProfile.imageUrl;
    this.description = updatedOpenProfile.description;

    return this;
  }
}
