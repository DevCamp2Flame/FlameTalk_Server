package com.devcamp.flametalk.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 기본적으로 제공되는 Exception 외에 사용하는 클래스입니다.
 */
@AllArgsConstructor
@Getter
public class CustomException extends RuntimeException {

  private final ErrorCode errorCode;
}