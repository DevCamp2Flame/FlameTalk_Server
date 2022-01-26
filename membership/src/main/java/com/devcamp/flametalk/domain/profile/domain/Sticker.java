package com.devcamp.flametalk.domain.profile.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Sticker {

  private Long stickerId;
  private double stickerX;
  private double stickerY;
}
