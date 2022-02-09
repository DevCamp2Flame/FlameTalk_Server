package com.devcamp.flametalk.domain.device.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 마지막으로 읽은 메시지 id 갱신 응답 Dto 입니다.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MessageResponseDto {

  private String userId;
  private String deviceId;
  private String curMaxMesssageId;
}