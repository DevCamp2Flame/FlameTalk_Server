package com.devcamp.flametalk.chatroom.global.error;

import com.devcamp.flametalk.chatroom.global.error.exception.ConflictException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

/**
 * 프로젝트 전역에 발생하는 Exception Handler 입니다.
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException e) {
    log.error("Method Argument Not Valid Exception", e);
    ErrorResponse response = ErrorResponse.from(ErrorCode.BAD_REQUEST);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  @ExceptionHandler(MissingServletRequestPartException.class)
  protected ResponseEntity<ErrorResponse> handleMissingServletRequestPartException(
      MissingServletRequestPartException e) {
    log.error("Missing Servlet RequestPart Exception" + e);
    ErrorResponse response = ErrorResponse.from(ErrorCode.BAD_REQUEST);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  @ExceptionHandler(ConflictException.class)
  protected ResponseEntity<ErrorResponse> handleForbiddenException(ConflictException e) {
    log.error(e.getMessage());
    ErrorResponse response = ErrorResponse.from(e.getErrorCode());
    return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
  }

  @ExceptionHandler(Exception.class)
  protected ResponseEntity<ErrorResponse> handleException(Exception e) {
    log.error("Internal Server Exception", e);
    ErrorResponse response = ErrorResponse.from(ErrorCode.INTERNAL_SERVER_ERROR);
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
  }
}
