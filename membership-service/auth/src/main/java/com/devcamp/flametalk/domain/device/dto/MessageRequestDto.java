package com.devcamp.flametalk.domain.device.dto;

import lombok.Getter;

/**
 * 마지막으로 읽은 메시지 id 갱신 요청 Dto 입니다.
 */
@Getter
public class MessageRequestDto {

  private String curMaxMesssageId;
}