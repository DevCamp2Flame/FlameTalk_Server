package com.devcamp.flametalk.domain.feed.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 피드 응답 결과의 메세지 타입에 대한 Enum 입니다.
 */
@AllArgsConstructor
@Getter
public enum FeedResponse {
  FEED_DELETE_SUCCESS(200, "피드 삭제 성공");

  private final int status;
  private final String message;
}
