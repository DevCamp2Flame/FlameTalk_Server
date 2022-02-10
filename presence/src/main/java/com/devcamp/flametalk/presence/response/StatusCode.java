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
  SUCCESS_ENTER(201, "채팅방 입장 기록 성공"),

  // 400 BAD_REQUEST : 잘못된 요청
  MISMATCH_TYPE(400, "메시지 타입이 올바르지 않습니다.");

  private final int status;
  private final String message;
}