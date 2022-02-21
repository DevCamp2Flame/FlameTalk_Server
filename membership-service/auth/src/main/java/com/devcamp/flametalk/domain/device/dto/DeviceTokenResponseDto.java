package com.devcamp.flametalk.domain.device.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 토큰 저장 응답 Dto 입니다.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class DeviceTokenResponseDto {

  private String userId;
  private String deviceId;
  private String deviceToken;
}