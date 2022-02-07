package com.devcamp.flametalk.chatroom.global.error.exception;

import com.devcamp.flametalk.chatroom.global.error.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 기본적으로 제공되는 Exception 외에 사용하는 클래스입니다.
 */
@AllArgsConstructor
@Getter
public class CustomException extends RuntimeException {

  private final HttpStatus httpStatus;
  private final ErrorCode errorCode;
}
