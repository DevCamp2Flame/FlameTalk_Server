package com.devcamp.flametalk.common.error;

import com.devcamp.flametalk.common.error.exception.CustomException;
import com.devcamp.flametalk.common.error.exception.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * 프로젝트 전역에 발생하는 Exception Handler 입니다.
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException e) {
    log.error("[Method Argument Not Valid Exception]" + e);
    return ErrorResponse.toResponseEntity(HttpStatus.BAD_REQUEST, ErrorCode.BAD_REQUEST);
  }

  @ExceptionHandler(MissingServletRequestPartException.class)
  protected ResponseEntity<ErrorResponse> handleMissingServletRequestPartException(
      MissingServletRequestPartException e) {
    log.error("[Missing Servlet RequestPart Exception]" + e);
    return ErrorResponse.toResponseEntity(HttpStatus.BAD_REQUEST, ErrorCode.BAD_REQUEST);
  }

  @ExceptionHandler(EntityNotFoundException.class)
  protected ResponseEntity<ErrorResponse> handleBusinessException(EntityNotFoundException e) {
    log.error("[Entity Not Found Exception]" + e.getMessage());
    return ErrorResponse.toResponseEntity(HttpStatus.NOT_FOUND, e.getErrorCode());
  }

  @ExceptionHandler(value = {CustomException.class})
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
