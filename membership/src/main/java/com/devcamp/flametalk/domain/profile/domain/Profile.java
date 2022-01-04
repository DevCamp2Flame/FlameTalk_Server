package com.devcamp.flametalk.domain.profile.domain;

import com.devcamp.flametalk.domain.file.domain.File;
import com.devcamp.flametalk.domain.user.domain.User;
import com.devcamp.flametalk.model.BaseTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Profile extends BaseTime {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @OneToOne
  @JoinColumn(name = "image_id")
  private File image;

  @OneToOne
  @JoinColumn(name = "background_image_id")
  private File backgroundImage;

  @OneToOne
  @JoinColumn(name = "sticker_id")
  private File stickerImage;

  private int stickerPositionX;

  private int stickerPositionY;

  private String description;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @NotNull
  private boolean isDefault;

  @Builder
  public Profile(long id, File image, File backgroundImage, File stickerImage,
      int stickerPositionX, int stickerPositionY, String description, User user, boolean isDefault) {
    this.id = id;
    this.image = image;
    this.backgroundImage = backgroundImage;
    this.stickerImage = stickerImage;
    this.stickerPositionX = stickerPositionX;
    this.stickerPositionY = stickerPositionY;
    this.description = description;
    this.user = user;
    this.isDefault = isDefault;
  }
}
