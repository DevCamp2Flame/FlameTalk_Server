package com.devcamp.flametalk.chatroom.domain.chatroom.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Chat API 응답 결과의 메세지 타입에 대한 Enum 입니다.
 */
@AllArgsConstructor
@Getter
public enum ResponseType {
  CHATROOM_CREATE_SUCCESS(201, "채팅방 생성 성공"),
  CHATROOM_JOIN_SUCCESS(201, "채팅방 입장 성공"),
  USER_CHATROOMS_SUCCESS(200, "채팅방 리스트 조회 성공"),
  CHATROOM_DETAIL_SUCCESS(200, "채팅방 상세 조회 성공"),
  CHATROOM_FILES_SUCCESS(200, "채팅방 파일 리스트 조회 성공"),
  CHATROOM_UPDATE_SUCCESS(200, "채팅방 수정 성공"),
  USER_CHATROOM_CLOSE_SUCCESS(200, "채팅방 닫기 성공"),
  USER_CHATROOM_DELETE_SUCCESS(200, "채팅방 나가기 성공");

  private final int status;
  private final String message;
}
