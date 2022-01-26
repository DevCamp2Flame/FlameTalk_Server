package com.devcamp.flametalk.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusCode {

  // 200 OK : 성공
  SUCCESS_READ_USER(200, "회원 정보 조회 성공"),
  SUCCESS_UPDATE_USER(200, "회원 정보 수정 성공"),
  SUCCESS_LEAVE_USER(200, "회원 탈퇴 성공"),
  VALID_EMAIL(200, "유효한 이메일입니다."),

  // 201 CREATED : 새로운 리소스 생성
  SUCCESS_LOGIN(201, "로그인 성공"),
  CREATED_USER(201, "회원 가입 성공"),
  CREATED_TOKEN(201, "토큰 갱신 성공");

  private final int status;
  private final String message;
}