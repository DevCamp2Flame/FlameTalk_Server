package com.devcamp.flametalk.global.error.exception;

import com.devcamp.flametalk.global.error.ErrorCode;
import lombok.Getter;

/**
 * DB에 저장된 리소스가 중복되는 경우의 예외 클래스입니다.
 */
@Getter
public class EntityExistsException extends RuntimeException {

  private final ErrorCode errorCode;

  public EntityExistsException(ErrorCode errorCode) {
    super(String.format("entity already exists error %s", errorCode.getMessage()));
    this.errorCode = errorCode;
  }
}
