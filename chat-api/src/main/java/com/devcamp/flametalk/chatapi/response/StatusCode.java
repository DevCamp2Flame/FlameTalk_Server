package com.devcamp.flametalk.chatapi.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusCode {

  // 200 OK : 성공

  // 201 CREATED : 새로운 리소스 생성
  SUCCESS_ENTER_CHATROOM(201, "채팅방 접근 성공"),
  CREATED_CHATROOM(201, "채팅방 생성 성공");
  
  private final int status;
  private final String message;
}