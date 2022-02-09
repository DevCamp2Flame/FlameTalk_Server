package com.devcamp.flametalk.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Gateway 에서 요청한 토큰의 내용과 일치하는 사용자 정보를 담는 객체입니다.
 */
@NoArgsConstructor
@Builder
@Getter
public class GatewayUserDto {

  private String userId;
  private String deviceId;

  public GatewayUserDto(String userId, String deviceId) {
    this.userId = userId;
    this.deviceId = deviceId;
  }
}
