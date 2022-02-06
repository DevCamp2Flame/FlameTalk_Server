package com.devcamp.flametalk.chatroom.domain.chatroom.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResponseType {
  CHATROOM_CREATE_SUCCESS(201, "채팅방 생성 성공");

  private final int status;
  private final String message;
}
