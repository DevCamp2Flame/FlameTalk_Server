package com.devcamp.flametalk.domain.profile.domain;

import com.devcamp.flametalk.domain.model.BaseTime;
import com.devcamp.flametalk.domain.user.domain.User;
import javax.persistence.Column;
import javax.persistence.Convert;
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
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
@Entity
public class Profile extends BaseTime {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String imageUrl;

  private String bgImageUrl;

  private String description;

  @Convert(converter = StickerConverter.class)
  private Sticker sticker;

  // TODO
  @Column(columnDefinition = "BOOLEAN")
  @NotNull
  private boolean isDefault;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @Builder
  public Profile(long id, String imageUrl, String bgImageUrl, Sticker sticker, String description,
      boolean isDefault, User user) {
    this.id = id;
    this.imageUrl = imageUrl;
    this.bgImageUrl = bgImageUrl;
    this.sticker = sticker;
    this.description = description;
    this.isDefault = isDefault;
    this.user = user;
  }

  public Profile update(Profile updatedProfile) {
    this.imageUrl = updatedProfile.imageUrl;
    this.bgImageUrl = updatedProfile.bgImageUrl;
    this.sticker = updatedProfile.sticker;
    this.description = updatedProfile.description;
    this.isDefault = updatedProfile.isDefault;
    this.user = updatedProfile.user;

    return this;
  }
}
