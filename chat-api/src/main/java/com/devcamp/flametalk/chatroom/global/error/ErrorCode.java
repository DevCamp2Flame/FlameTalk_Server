package com.devcamp.flametalk.chatroom.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 모든 예외 케이스를 관리하는 열거형 클래스입니다.
 */
@AllArgsConstructor
@Getter
public enum ErrorCode {

  // 500 INTERNAL_SERVER_ERROR : 서버 오류
  INTERNAL_SERVER_ERROR(500, "SERVER_ERROR", "서버 오류입니다.");

  private final int status;
  private final String error;
  private final String message;
}