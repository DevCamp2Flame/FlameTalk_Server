package com.devcamp.flametalk.domain.profile.dto;

import com.devcamp.flametalk.domain.profile.domain.Profile;
import com.devcamp.flametalk.domain.profile.domain.Sticker;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 프로필 상세 정보 응답을 위한 클래스입니다.
 */
@AllArgsConstructor
@Getter
public class ProfileDetailResponse {

  private Long id;
  private String imageUrl;
  private String bgImageUrl;
  private List<Sticker> sticker;
  private String description;
  private boolean isDefault;
  private LocalDateTime updatedDate;

  /**
   * Profile 엔티티를 응답 객체로 생성하는 정적 팩토리 메소드입니다.
   *
   * @param profile Profile 엔티티
   * @return 프로필 상세 정보 응답 객체
   */
  public static ProfileDetailResponse from(Profile profile) {
    return new ProfileDetailResponse(
        profile.getId(),
        profile.getImageUrl(),
        profile.getBgImageUrl(),
        profile.getSticker(),
        profile.getDescription(),
        profile.isDefault(),
        profile.getUpdatedAt());
  }
}
