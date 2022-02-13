package com.devcamp.flametalk.chatroom.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 모든 예외 케이스를 관리하는 열거형 클래스입니다.
 */
@AllArgsConstructor
@Getter
public enum ErrorCode {
  BAD_REQUEST(400, "BAD_REQUEST", "잘못된 요청입니다."),

  USER_NOT_FOUND(404, "NOT_FOUND", "존재하지 않는 유저입니다."),
  USER_CHATROOM_NOT_FOUND(404, "NOT_FOUND", "존재하지 않는 유저 채팅방입니다."),
  CHATROOM_NOT_FOUND(404, "NOT_FOUND", "존재하지 않는 채팅방입니다."),

  HOST_USER_CONFLICT_EXCEPTION(409, "HOST ID CONFLICT", "현재 유저와 채팅방 개설자 유저 ID가 다릅니다."),

  // 500 INTERNAL_SERVER_ERROR : 서버 오류
  INTERNAL_SERVER_ERROR(500, "SERVER_ERROR", "서버 오류입니다.");

  private final int status;
  private final String error;
  private final String message;
}
