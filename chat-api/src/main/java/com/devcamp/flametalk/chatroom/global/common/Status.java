package com.devcamp.flametalk.chatroom.global.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 공통 응답 결과의 코드와 메세지에 대한 Enum 입니다.
 */
@AllArgsConstructor
@Getter
public enum Status {

  // 200 OK : 성공

  // 201 CREATED : 새로운 리소스 생성
  SUCCESS_ENTER_CHATROOM(201, "채팅방 접근 성공"),
  CREATED_CHATROOM(201, "채팅방 생성 성공");
  
  private final int code;
  private final String message;
}
