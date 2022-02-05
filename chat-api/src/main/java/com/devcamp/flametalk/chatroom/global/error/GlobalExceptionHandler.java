package com.devcamp.flametalk.chatroom.global.error;

import static com.devcamp.flametalk.chatroom.global.error.ErrorCode.INTERNAL_SERVER_ERROR;

import com.devcamp.flametalk.chatroom.global.error.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * 프로젝트 전역에 발생하는 Exception Handler 입니다.
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(CustomException.class)
  protected ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
    log.error("[HandleCustomException] " + e.getErrorCode());
    return ErrorResponse.toResponseEntity(e.getHttpStatus(), e.getErrorCode());
  }

  @ExceptionHandler(Exception.class)
  protected ResponseEntity<ErrorResponse> handleException(Exception e) {
    log.error("[HandleException] " + e);
    return ErrorResponse.toResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR);
  }
}
