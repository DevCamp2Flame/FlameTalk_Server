package com.devcamp.flametalk.domain.profile.dto;

import com.devcamp.flametalk.domain.profile.domain.Profile;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ProfileResponse {
  private Long id;
  private Long imageId;
  private Long backgroundImageId;
  private Long stickerId;
  private int stickerPositionX;
  private int stickerPositionY;
  private String description;
  private boolean isDefault;


  public static ProfileResponse of(Profile profile) {
    return new ProfileResponse(
        profile.getId(),
        profile.getImage().getId(),
        profile.getBackgroundImage().getId(),
        profile.getStickerImage().getId(),
        profile.getStickerPositionX(),
        profile.getStickerPositionY(),
        profile.getDescription(),
        profile.isDefault());
  }
}
