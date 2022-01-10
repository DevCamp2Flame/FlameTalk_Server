package com.devcamp.flametalk.domain.file.domain;

import com.devcamp.flametalk.domain.model.BaseTime;
import com.devcamp.flametalk.domain.profile.domain.Profile;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class File extends BaseTime {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @NotNull
  private String content;

  @NotNull
  private String type;

  private String thumbnail;

  @OneToOne(mappedBy = "image")
  private Profile profileImage;

  @OneToOne(mappedBy = "backgroundImage")
  private Profile profileBackgroundImage;

  @OneToOne(mappedBy = "stickerImage")
  private Profile profileStickerImage;
}
