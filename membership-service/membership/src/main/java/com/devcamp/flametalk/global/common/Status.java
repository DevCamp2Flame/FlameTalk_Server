package com.devcamp.flametalk.global.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 공통 응답 결과의 코드와 메세지에 대한 Enum 입니다.
 */
@AllArgsConstructor
@Getter
public enum Status {

  // 200 OK : 성공
  FRIENDS_SUCCESS(200, "친구 리스트 조회 성공"),
  UPDATED_FRIEND(200, "친구 관계 수정 성공"),

  // 201 CREATED : 새로운 리소스 생성
  CREATED_FRIEND(201, "친구 추가 성공"),
  CREATED_CONTACT_FRIENDS(201, "연락처 전체 친구 추가 성공"),

  // FAIL
  FRIEND_NOT_FOUND(404, "존재하지 않는 유저는 친구로 추가할 수 없습니다.");

  private final int code;
  private final String message;
}
