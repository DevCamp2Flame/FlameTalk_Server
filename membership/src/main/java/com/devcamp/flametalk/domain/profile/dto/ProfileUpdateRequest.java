package com.devcamp.flametalk.domain.profile.dto;

import com.devcamp.flametalk.domain.file.domain.File;
import com.devcamp.flametalk.domain.profile.domain.Profile;
import com.devcamp.flametalk.domain.user.domain.User;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ProfileUpdateRequest {
  @NotNull
  private long id;

  private long imageId;
  private long backgroundImageId;
  private long stickerId;
  private int stickerPositionX;
  private int stickerPositionY;
  private String description;

  @NotNull
  private String userId;

  @NotNull
  private Boolean isDefault;

  public Profile toProfile(User user, File image, File backgroundImage, File sticker) {
    return Profile.builder()
        .id(id)
        .image(image)
        .backgroundImage(backgroundImage)
        .stickerImage(sticker)
        .stickerPositionX(stickerPositionX)
        .stickerPositionY(stickerPositionY)
        .description(description)
        .user(user)
        .isDefault(isDefault)
        .build();
  }
}
