package com.devcamp.flametalk.global.error;

import com.devcamp.flametalk.global.error.exception.CustomException;
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
    log.error("[handleCustomException throw CustomException] {}", e.getErrorCode());
    return ErrorResponse.toResponseEntity(HttpStatus.BAD_REQUEST, e.getErrorCode());
  }

  @ExceptionHandler(Exception.class)
  protected ResponseEntity<ErrorResponse> handleException(Exception e) {
    log.error("[Internal Server Exception]" + e);
    return ErrorResponse.toResponseEntity(HttpStatus.BAD_REQUEST, ErrorCode.INTERNAL_SERVER_ERROR);
  }
}
