package com.devcamp.flametalk.gateway.dto;

import lombok.Getter;
import lombok.ToString;

/**
 * 토큰과 일치하는 사용자 정보를 저장하는 객체입니다.
 */
@ToString
@Getter
public class UserDto {

  private String userId;
  private String deviceId;
}
