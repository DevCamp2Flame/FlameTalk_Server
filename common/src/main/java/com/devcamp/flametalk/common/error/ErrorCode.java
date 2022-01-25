package com.devcamp.flametalk.common.error;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.TEMPORARY_REDIRECT;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 모든 예외 케이스를 관리하는 열거형 클래스입니다.
 */
@AllArgsConstructor
@Getter
public enum ErrorCode {

  // 307 TEMPORARY_REDIRECT : 임시 이동
  REDIRECT_TO_LOGIN(307, "TEMPORARY_REDIRECT", "다시 로그인 하세요. /api/auth/signin"),
  REDIRECT_TO_TOKEN(307, "TEMPORARY_REDIRECT", "토큰을 다시 발급하세요. /api/auth/token"),

  // 400 BAD_REQUEST : 잘못된 요청
  INVALID_REFRESH_TOKEN(400, "BAD_REQUEST", "리프레시 토큰이 유효하지 않습니다."),
  MISMATCH_REFRESH_TOKEN(400, "BAD_REQUEST", "리프레시 토큰의 사용자 정보가 일치하지 않습니다."),
  MISMATCH_PASSWORD(400, "BAD_REQUEST", "비밀번호가 일치하지 않습니다."),
  LEAVE_USER(400, "BAD_REQUEST", "탈퇴한 사용자입니다."),

  // 404 NOT_FOUND : Resource 를 찾을 수 없음
  USER_NOT_FOUND(404, "NOT_FOUND", "해당 사용자 정보를 찾을 수 없습니다."),
  TOKEN_NOT_FOUND(404, "NOT_FOUND", "저장된 토큰을 찾을 수 없습니다."),

  // 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재
  DUPLICATE_PHONE_NUMBER(409, "CONFLICT", "이미 가입된 전화번호입니다."),
  DUPLICATE_EMAIL(409, "CONFLICT", "이미 가입된 이메일입니다.");

  private final int status;
  private final String error;
  private final String message;
}