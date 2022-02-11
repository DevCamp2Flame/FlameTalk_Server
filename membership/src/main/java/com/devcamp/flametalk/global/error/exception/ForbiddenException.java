package com.devcamp.flametalk.global.error.exception;

import com.devcamp.flametalk.global.error.ErrorCode;
import lombok.Getter;

/**
 * DB에 저장된 리소스에 대해 접근 권한이 없는 경우의 예외 클래스입니다.
 */
@Getter
public class ForbiddenException extends RuntimeException {

  private final ErrorCode errorCode;

  public ForbiddenException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }
}
