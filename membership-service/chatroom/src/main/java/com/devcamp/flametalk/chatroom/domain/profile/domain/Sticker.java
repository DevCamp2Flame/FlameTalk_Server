package com.devcamp.flametalk.chatroom.domain.profile.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 프로필 엔티티에 JSON 형식으로 저장되는 스티커 객체입니다.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Sticker {

  private Long stickerId;
  private double stickerX;
  private double stickerY;
}