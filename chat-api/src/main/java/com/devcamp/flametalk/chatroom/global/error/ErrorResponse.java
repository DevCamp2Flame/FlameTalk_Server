package com.devcamp.flametalk.chatroom.global.error;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 사용자에게 JSON 형식으로 보여주기 위한 에러 응답 형식 지정 클래스입니다.
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {

  private int status;
  private String message;
  private String error;
  private String code;
  private LocalDateTime timestamp;

  /**
   * 정적 팩토리 메소드입니다.
   *
   * @param error 예외 종류
   * @return 클라이언트가 확인가능한 response
   */
  public static ErrorResponse from(ErrorCode error) {
    ErrorResponse response = new ErrorResponse();
    response.status = error.getStatus();
    response.message = error.getMessage();
    response.error = error.getError();
    response.code = error.name();
    response.timestamp = LocalDateTime.now();
    return response;
  }
}
