package com.devcamp.flametalk.domain.user.dto;

import lombok.Getter;

/**
 * 로그인 요청 Dto 입니다.
 */
@Getter
public class SignInRequestDto {

  private String email;
  private String password; // social 로그인이라면, ""
  private String social;
  private String deviceId;
}