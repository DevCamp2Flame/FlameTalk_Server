package com.devcamp.flametalk.domain.feed.controller;

import com.devcamp.flametalk.domain.feed.domain.FeedResponse;
import com.devcamp.flametalk.domain.feed.service.FeedService;
import com.devcamp.flametalk.global.common.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Membership 서버의 Feed API 처리 컨트롤러입니다.
 */
@Slf4j
@CrossOrigin("*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/membership/feed")
public class FeedController {

  private final FeedService feedService;

  /**
   * 요청받은 id에 해당하는 피드 사진의 공개 여부를 반전시킵니다.
   *
   * @param feedId 피드 id
   * @return 공개 여부 결과에 따른 응답 정보
   */
  @PutMapping("/lock/{feedId}")
  public ResponseEntity<CommonResponse> updateLock(@PathVariable Long feedId) {
    feedService.updateLock(feedId);
    log.info("update feed lock {}", feedId);

    CommonResponse response = new CommonResponse();
    response.success(FeedResponse.FEED_REVERSE_LOCK_SUCCESS.getMessage());
    return ResponseEntity.ok().body(response);
  }

  /**
   * 요청받은 id에 해당하는 피드를 삭제합니다.
   *
   * @param feedId 피드 id
   * @return 삭제 결과에 따른 응답 정보
   */
  @DeleteMapping("/{feedId}")
  public ResponseEntity<CommonResponse> deleteById(@PathVariable Long feedId) {
    feedService.deleteById(feedId);
    log.info("delete feed {}", feedId);

    CommonResponse response = new CommonResponse();
    response.success(FeedResponse.FEED_DELETE_SUCCESS.getMessage());
    return ResponseEntity.ok().body(response);
  }
}
