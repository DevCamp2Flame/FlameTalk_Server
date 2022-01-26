package com.devcamp.flametalk.global.error;

import static com.devcamp.flametalk.global.error.ErrorCode.EXPIRED_TOKEN;
import static com.devcamp.flametalk.global.error.ErrorCode.INTERNAL_SERVER_ERROR;
import static com.devcamp.flametalk.global.error.ErrorCode.INVALID_SIGNATURE;
import static com.devcamp.flametalk.global.error.ErrorCode.INVALID_TOKEN;

import com.devcamp.flametalk.global.error.exception.CustomException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
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
    return ErrorResponse.toResponseEntity(HttpStatus.BAD_REQUEST, e.getErrorCode());
  }

  @ExceptionHandler({SignatureException.class, MalformedJwtException.class})
  protected ResponseEntity<ErrorResponse> handleJwtException(Exception e) {
    log.error("[Invalid token] " + e);
    return ErrorResponse.toResponseEntity(HttpStatus.UNAUTHORIZED, INVALID_SIGNATURE);
  }

  @ExceptionHandler(ExpiredJwtException.class)
  protected ResponseEntity<ErrorResponse> handleJwtExpiredException(Exception e) {
    log.error("[Expired token] " + e);
    return ErrorResponse.toResponseEntity(HttpStatus.UNAUTHORIZED, EXPIRED_TOKEN);
  }

  @ExceptionHandler(Exception.class)
  protected ResponseEntity<ErrorResponse> handleException(Exception e) {
    log.error("[HandleException] " + e);
    return ErrorResponse.toResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR);
  }
}
