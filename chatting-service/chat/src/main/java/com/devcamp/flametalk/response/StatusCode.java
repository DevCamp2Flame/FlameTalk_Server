package com.devcamp.flametalk.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 정상 응답관련 상태코드와 메시지를 관리하는 열거형 클래스입니다.
 */
@AllArgsConstructor
@Getter
public enum StatusCode {

  // 200 OK : 성공
  SUCCESS_READ_MESSAGE(200, "메시지 조회 성공");

  private final int status;
  private final String message;
}