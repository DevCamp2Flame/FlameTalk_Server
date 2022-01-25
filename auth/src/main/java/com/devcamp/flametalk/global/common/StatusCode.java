package com.devcamp.flametalk.global.common;

import static org.springframework.http.HttpStatus.CONTINUE;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum StatusCode {

    // 200 OK : 성공
    SUCCESS_READ_USER(OK, "회원 정보 조회 성공"),
    SUCCESS_UPDATE_USER(OK, "회원 정보 수정 성공"),
    SUCCESS_LEAVE_USER(OK, "회원 탈퇴 성공"),
    VALID_EMAIL(OK, "유효한 이메일입니다."),

    // 201 CREATED : 새로운 리소스 생성
    SUCCESS_LOGIN(CREATED, "로그인 성공"),
    CREATED_USER(CREATED, "회원 가입 성공"),
    CREATED_TOKEN(CREATED, "토큰 갱신 성공");

    private final HttpStatus httpStatus;
    private final String message;
}