package com.devcamp.flametalk.common.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 사용자 상태에 대한 열거형 클래스입니다.
 */
@Getter
@RequiredArgsConstructor
public enum Status {

  GUEST((short) 0, "ROLE_GUEST", "준회원"),
  USER((short) 1, "ROLE_USER", "이메일 인증을 완료한 정회원"),
  ADMIN((short) 2, "ROLE_ADMIN", "관리자"),
  LEAVE((short) 3, "STAT_LEAVE", "탈퇴");

  private final short key;
  private final String name;
  private final String description;
}