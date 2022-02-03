package com.devcamp.flametalk.domain.openprofile.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 오픈 프로필 응답 결과의 메세지 타입에 대한 Enum 입니다.
 */
@AllArgsConstructor
@Getter
public enum OpenProfileResponse {
  OPEN_PROFILE_CREATE_SUCCESS(201, "오픈 프로필 생성 성공"),
  OPEN_PROFILE_UPDATE_SUCCESS(200, "오픈 프로필 수정 성공");

  private final int status;
  private final String message;
}
