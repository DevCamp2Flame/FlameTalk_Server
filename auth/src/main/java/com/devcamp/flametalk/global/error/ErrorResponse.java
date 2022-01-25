package com.devcamp.flametalk.global.error;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

/**
 * 사용자에게 JSON 형식으로 보여주기 위한 에러 응답 형식 지정 클래스입니다.
 */
@Getter
@Builder
public class ErrorResponse {

  private final LocalDateTime timestamp = LocalDateTime.now();
  private final int status;
  private final String error;
  private final String code;
  private final String message;

  /**
   * ErrorResponse 를 ResponseEntity 형식으로 반환하기 위한 메소드입니다.
   *
   * @param errorCode 에러 코드
   * @return ErrorCode 의 정보를 담아 ResponseEntity 를 반환
   */
  public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCode errorCode) {
    return ResponseEntity
        .status(errorCode.getHttpStatus())
        .body(ErrorResponse.builder()
            .status(errorCode.getHttpStatus().value())
            .error(errorCode.getHttpStatus().name())
            .code(errorCode.name())
            .message(errorCode.getMessage())
            .build()
        );
  }
}