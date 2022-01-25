package com.devcamp.flametalk.common.error.exception;

import com.devcamp.flametalk.common.error.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * DB에 저장된 리소스를 찾을 수 없는 경우의 예외 클래스입니다.
 */
@AllArgsConstructor
@Getter
public class EntityNotFoundException extends RuntimeException {

  private final ErrorCode errorCode;
}
