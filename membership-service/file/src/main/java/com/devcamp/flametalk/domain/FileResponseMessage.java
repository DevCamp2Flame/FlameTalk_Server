package com.devcamp.flametalk.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 응답 결과의 메세지 타입에 대한 Enum 입니다.
 */
@AllArgsConstructor
@Getter
public enum FileResponseMessage {
  FILE_CREATE_SUCCESS("파일 생성 성공"),
  FILE_DELETE_SUCCESS("파일 삭제 성공"),
  FILE_DETAIL_SUCCESS("파일 조회 성공"),
  CHATROOM_FILES_SUCCESS("채팅방 파일 리스트 조회 성공"),

  FILE_CAPACITY_FAIL("파일 용량 초과");

  private final String message;
}
