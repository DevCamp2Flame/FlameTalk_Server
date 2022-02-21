package com.devcamp.flametalk.domain.friend.domain;

import com.devcamp.flametalk.domain.profile.domain.Profile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Friend 엔티티에 JSON 형식으로 저장되는 프로필 프리뷰 객체입니다.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Preview {

  private Long profileId;
  private String imageUrl;
  private String description;

  /**
   * 프로필로부터 프리뷰를 생성합니다.
   *
   * @param profile 프로필 엔티티
   * @return 프리뷰 객체
   */
  public static Preview from(Profile profile) {
    return new Preview(
        profile.getId(),
        profile.getImageUrl(),
        profile.getDescription()
    );
  }
}
