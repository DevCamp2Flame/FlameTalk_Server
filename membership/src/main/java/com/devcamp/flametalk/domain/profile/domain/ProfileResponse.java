package com.devcamp.flametalk.domain.profile.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 프로필 응답 결과의 메세지 타입에 대한 Enum 입니다.
 */
@AllArgsConstructor
@Getter
public enum ProfileResponse {
  PROFILE_CREATE_SUCCESS(201, "프로필 생성 성공"),
  PROFILE_DETAIL_SUCCESS(200, "프로필 조회 성공"),
  PROFILE_UPDATE_SUCCESS(200, "프로필 수정 성공");

  private final int status;
  private final String message;
}
