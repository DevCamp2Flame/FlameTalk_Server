package com.devcamp.flametalk.domain.openprofile.dto;

import com.devcamp.flametalk.domain.openprofile.domain.OpenProfile;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 오픈 프로필 상세 정보 응답을 위한 클래스입니다.
 */
@AllArgsConstructor
@Getter
public class OpenProfileDetailResponse {

  private Long openProfileId;
  private String userId;
  private String nickname;
  private String imageUrl;
  private String description;
  private LocalDateTime createdDate;
  private LocalDateTime updatedDate;

  /**
   * OpenProfile 엔티티를 응답 객체로 생성하는 정적 팩토리 메소드입니다.
   *
   * @param openProfile OpenProfile 엔티티
   * @return 오픈 프로필 상세 정보 응답 객체
   */
  public static OpenProfileDetailResponse from(OpenProfile openProfile) {
    return new OpenProfileDetailResponse(
        openProfile.getId(),
        openProfile.getOpenProfileUser().getId(),
        openProfile.getNickname(),
        openProfile.getImageUrl(),
        openProfile.getDescription(),
        openProfile.getCreatedAt(),
        openProfile.getUpdatedAt()
    );
  }
}
