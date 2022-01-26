package com.devcamp.flametalk.domain.profile.dto;

import com.devcamp.flametalk.domain.profile.domain.Profile;
import com.devcamp.flametalk.domain.profile.domain.Sticker;
import com.devcamp.flametalk.domain.user.domain.User;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileRequest {

  @NotNull
  private String userId;

  private String imageUrl;
  private String bgImageUrl;
  private String description;
  private Sticker sticker;

  @NotNull
  private boolean isDefault;

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
}
