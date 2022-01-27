package com.devcamp.flametalk.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 모든 예외 케이스를 관리하는 열거형 클래스입니다.
 */
@AllArgsConstructor
@Getter
public enum ErrorCode {

  // 307 TEMPORARY_REDIRECT : 임시 이동
  REDIRECT_TO_LOGIN(307, "TEMPORARY_REDIRECT", "다시 로그인 하세요. /api/auth/signin"),

  // 400 BAD_REQUEST : 잘못된 요청
  MISMATCH_PASSWORD(400, "BAD_REQUEST", "비밀번호가 일치하지 않습니다."),
  LEAVE_USER(400, "BAD_REQUEST", "탈퇴한 사용자입니다."),
  BAD_REQUEST(400, "BAD_REQUEST", "잘못된 요청 파라미터입니다."),

  // 401 UNAUTHORIZED : 권한 없음
  INVALID_TOKEN(401, "UNAUTHORIZED", "토큰이 유효하지 않습니다."),
  INVALID_SIGNATURE(401, "UNAUTHORIZED", "토큰의 서명 또는 구조가 유효하지 않습니다."),
  EXPIRED_TOKEN(401, "UNAUTHORIZED", "토큰의 유효 기간이 지났습니다."),

  // 404 NOT_FOUND : Resource 를 찾을 수 없음
  USER_NOT_FOUND(404, "NOT_FOUND", "해당 사용자 정보를 찾을 수 없습니다."),
  TOKEN_NOT_FOUND(404, "NOT_FOUND", "저장된 토큰을 찾을 수 없습니다."),

  // 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재
  DUPLICATE_PHONE_NUMBER(409, "CONFLICT", "이미 가입된 전화번호입니다."),
  DUPLICATE_EMAIL(409, "CONFLICT", "이미 가입된 이메일입니다."),

  // 500 INTERNAL_SERVER_ERROR : 서버 오류
  INTERNAL_SERVER_ERROR(500, "SERVER_ERROR", "서버 오류입니다.");

  private final int status;
  private final String error;
  private final String message;
}