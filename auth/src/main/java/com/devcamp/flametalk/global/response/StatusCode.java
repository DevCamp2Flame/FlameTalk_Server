package com.devcamp.flametalk.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 정상 응답관련 상태코드와 메시지를 관리하는 열거형 클래스입니다.
 */
@AllArgsConstructor
@Getter
public enum StatusCode {

  // 200 OK : 성공
  SUCCESS_READ_USER(200, "회원 정보 조회 성공"),
  SUCCESS_UPDATE_USER(200, "회원 정보 수정 성공"),
  SUCCESS_LEAVE(200, "회원 탈퇴 성공"),
  VALID_EMAIL(200, "유효한 이메일"),

  // 201 CREATED : 새로운 리소스 생성
  SUCCESS_LOGIN(201, "로그인 성공"),
  CREATED_USER(201, "회원 가입 성공"),
  CREATED_TOKEN(201, "토큰 갱신 성공"),

  // 307 TEMPORARY_REDIRECT : 임시 이동
  REDIRECT_TO_LOGIN(307, "다시 로그인 하세요. /api/auth/signin"),

  // 400 BAD_REQUEST : 잘못된 요청
  MISMATCH_PASSWORD(400, "비밀번호가 일치하지 않습니다."),
  LEAVE_USER(400, "탈퇴한 사용자입니다."),

  // 401 UNAUTHORIZED : 권한 없음
  INVALID_TOKEN(401, "토큰이 유효하지 않습니다."),

  // 404 NOT_FOUND : Resource 를 찾을 수 없음
  USER_NOT_FOUND(404, "해당 사용자 정보를 찾을 수 없습니다."),

  // 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재
  DUPLICATE_PHONE_NUMBER(409, "이미 가입된 전화번호입니다."),
  DUPLICATE_EMAIL(409, "이미 가입된 이메일입니다.");

  private final int status;
  private final String message;
}