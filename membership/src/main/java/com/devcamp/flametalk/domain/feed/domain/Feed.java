package com.devcamp.flametalk.domain.feed.domain;

import com.devcamp.flametalk.domain.model.BaseTime;
import com.devcamp.flametalk.domain.profile.domain.Profile;
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
 * Feed 엔티티 입니다.
 */
@Getter
@NoArgsConstructor
@Entity
public class Feed extends BaseTime {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  private String imageUrl;

  @NotNull
  private Boolean isBackground;

  @Column(nullable = false, columnDefinition = "TINYINT", length = 1)
  private Boolean isLock;

  @ManyToOne
  @JoinColumn(name = "profile_id")
  private Profile profile;

  /**
   * Feed 엔티티의 빌더입니다.
   *
   * @param id           피드 id
   * @param imageUrl     피드 이미지 S3 URL
   * @param isBackground 피드 이미지의 배경사진 여부
   * @param isLock       피드 이미지의 공개 여부
   * @param profile      피드에 해당하는 프로필
   */
  @Builder
  public Feed(Long id, String imageUrl, Boolean isBackground, Boolean isLock, Profile profile) {
    this.id = id;
    this.imageUrl = imageUrl;
    this.isBackground = isBackground;
    this.isLock = isLock;
    this.profile = profile;
  }

  public void reverseLock() {
    this.isLock = !isLock;
  }
}
