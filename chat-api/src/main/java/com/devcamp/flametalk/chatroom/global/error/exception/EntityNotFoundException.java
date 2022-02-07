package com.devcamp.flametalk.chatroom.global.error.exception;

import com.devcamp.flametalk.chatroom.global.error.ErrorCode;
import lombok.Getter;

/**
 * DB에 저장된 리소스를 찾을 수 없는 경우의 예외 클래스입니다.
 */
@Getter
public class EntityNotFoundException extends RuntimeException {

  private final ErrorCode errorCode;

  public EntityNotFoundException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }
}