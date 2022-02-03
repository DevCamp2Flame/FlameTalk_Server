package com.devcamp.flametalk.domain.profile.dto;

import com.devcamp.flametalk.domain.feed.domain.Feed;
import com.devcamp.flametalk.domain.profile.domain.Profile;
import com.devcamp.flametalk.domain.profile.domain.Sticker;
import com.devcamp.flametalk.domain.user.domain.User;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 프로필 생성과 수정을 위한 클래스입니다.
 */
@AllArgsConstructor
@Getter
public class ProfileRequest {

  @NotNull
  private String userId;

  private String imageUrl;
  private String bgImageUrl;
  private String description;
  private List<Sticker> sticker;

  @NotNull
  private boolean isDefault;

  /**
   * 인스턴스 정보를 Profile 엔티티로 반환하는 메소드입니다.
   *
   * @param user userId에 해당하는 user
   * @return Profile 엔티티
   */
  public Profile toProfile(User user) {
    return Profile.builder()
        .user(user)
        .imageUrl(imageUrl)
        .bgImageUrl(bgImageUrl)
        .description(description)
        .sticker(sticker)
        .isDefault(isDefault)
        .build();
  }

  /**
   * 프로필 생성 시 요청된 사진 정보를 바탕으로 피드 객체를 생성합니다.
   *
   * @param profile 현재 프로필 객체
   * @return 생성된 피드 객체
   */
  public Feed imageToFeed(Profile profile) {
    return Feed.builder()
        .imageUrl(imageUrl)
        .isBackground(false)
        .isLock(false)
        .profile(profile)
        .build();
  }

  /**
   * 프로필 생성 시 요청된 배경 사진 정보를 바탕으로 피드 객체를 생성합니다.
   *
   * @param profile 현재 프로필 객체
   * @return 생성된 피드 객체
   */
  public Feed bgImageToFeed(Profile profile) {
    return Feed.builder()
        .imageUrl(bgImageUrl)
        .isBackground(true)
        .isLock(false)
        .profile(profile)
        .build();
  }
}
