package com.devcamp.flametalk.domain.feed.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 피드 응답 결과의 메세지 타입에 대한 Enum 입니다.
 */
@AllArgsConstructor
@Getter
public enum FeedResponse {
  FEEDS_SUCCESS(200, "피드 리스트 조회 성공"),
  FEED_DELETE_SUCCESS(200, "피드 삭제 성공"),
  FEED_REVERSE_LOCK_SUCCESS(200, "피드 사진 공개 여부 변환 성공");

  private final int status;
  private final String message;
}
