package com.devcamp.flametalk.response;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * 사용자에게 JSON 형식으로 보여주기 위한 응답 형식 지정 클래스입니다.
 */
@Data
@Getter
@Builder
public class DefaultResponse<T> {

  private final int status;
  private final String message;
  private final T data;

  /**
   * DefaultResponse 를 ResponseEntity 형식으로 반환하기 위한 메소드입니다.
   *
   * @param statusCode 응답 코드
   * @return StatusCode 의 정보를 담아 ResponseEntity 를 반환
   */
  public static <T> ResponseEntity<DefaultResponse<T>> toResponseEntity(HttpStatus httpStatus,
      StatusCode statusCode, T data) {
    return ResponseEntity
        .status(httpStatus)
        .body(DefaultResponse.<T>builder()
            .status(statusCode.getStatus())
            .message(statusCode.getMessage())
            .data(data)
            .build()
        );
  }
}