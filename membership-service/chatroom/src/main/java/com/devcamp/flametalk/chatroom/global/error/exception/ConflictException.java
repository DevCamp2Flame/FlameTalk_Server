package com.devcamp.flametalk.chatroom.global.error.exception;

import com.devcamp.flametalk.chatroom.global.error.ErrorCode;
import lombok.Getter;

/**
 * 요청된 리소스가 서버의 현재 상태와 충돌된 경우의 예외 클래스입니다.
 */
@Getter
public class ConflictException extends RuntimeException {

  private final ErrorCode errorCode;

  public ConflictException(ErrorCode errorCode) {
    super(String.format("Conflict Exception: %s", errorCode.getMessage()));
    this.errorCode = errorCode;
  }
}
