package com.devcamp.flametalk.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * membership 서버의 모든 예외를 관리하는 Enum 입니다.
 */
@AllArgsConstructor
@Getter
public enum ErrorCode {
  BAD_REQUEST(400, "BAD_REQUEST", "잘못된 요청입니다."),

  USER_NOT_FOUND(404, "NOT_FOUND", "존재하지 않는 유저입니다."),
  PROFILE_NOT_FOUND(404, "NOT_FOUND", "존재하지 않는 프로필입니다."),

  INTERNAL_SERVER_ERROR(500, "SERVER_ERROR", "서버에서 오류가 발생하였습니다.");

  private final int status;
  private final String error;
  private final String message;
}
