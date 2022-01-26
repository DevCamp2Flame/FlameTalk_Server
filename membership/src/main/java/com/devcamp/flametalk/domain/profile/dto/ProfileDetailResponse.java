package com.devcamp.flametalk.domain.profile.dto;

import com.devcamp.flametalk.domain.profile.domain.Profile;
import com.devcamp.flametalk.domain.profile.domain.Sticker;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@AllArgsConstructor
@Getter
public class ProfileDetailResponse {

  private Long id;
  private String imageUrl;
  private String bgImageUrl;
  private Sticker sticker;
  private String description;
  private boolean isDefault;
  private LocalDateTime updatedDate;

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
