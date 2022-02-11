package com.devcamp.flametalk.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 모든 예외 케이스를 관리하는 열거형 클래스입니다.
 */
@AllArgsConstructor
@Getter
public enum ErrorCode {

  // 400 BAD_REQUEST : 잘못된 요청
  BAD_REQUEST(400, "BAD_REQUEST", "잘못된 요청 파라미터입니다."),

  // 401 UNAUTHORIZED : 권한 없음
  INVALID_SIGNATURE(401, "UNAUTHORIZED", "토큰의 서명 또는 구조가 유효하지 않습니다."),
  EXPIRED_TOKEN(401, "UNAUTHORIZED", "토큰의 유효 기간이 지났습니다."),

  // 404 NOT_FOUND : Resource 를 찾을 수 없음
  USER_NOT_FOUND_BY_TOKEN(404, "NOT_FOUND", "저장된 토큰을 찾을 수 없습니다."),

  // 500 INTERNAL_SERVER_ERROR : 서버 오류
  INTERNAL_SERVER_ERROR(500, "SERVER_ERROR", "서버 오류입니다.");

  private final int status;
  private final String error;
  private final String message;
}