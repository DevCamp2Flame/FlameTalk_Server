package com.devcamp.flametalk.presence.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 정상 응답관련 상태코드와 메시지를 관리하는 열거형 클래스입니다.
 */
@ToString
@AllArgsConstructor
@Getter
public enum StatusCode {

  // 200 OK : 성공
  SUCCESS_EXIT(200, "채팅방 퇴장 기록 성공"),

  // 201 CREATED : 새로운 리소스 생성
  SUCCESS_ENTER(201, "채팅방 입장 기록 성공");

  private final int status;
  private final String message;
}