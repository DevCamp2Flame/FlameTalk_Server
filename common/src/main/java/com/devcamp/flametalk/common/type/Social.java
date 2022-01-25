package com.devcamp.flametalk.common.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 로그인 타입에 대한 열거형 클래스입니다.
 */
@Getter
@RequiredArgsConstructor
public enum Social {

  LOGIN((short) 0, "LOGIN", "자체 로그인"),
  GOOGLE((short) 1, "GOOGLE", "Goggle 소셜 로그인");

  private final short key;
  private final String name;
  private final String description;

  /**
   * String 문자열을 Social 타입으로 변환합니다.
   *
   * @param social 로그인 타입 이름
   * @return LOGIN or GOOGLE
   */
  public static Social stringToSocial(String social) {
    if (social.equals("LOGIN")) {
      return LOGIN;
    }
    return GOOGLE;
  }
}