package com.devcamp.flametalk.domain.feed.controller;

import com.devcamp.flametalk.domain.feed.domain.FeedResponse;
import com.devcamp.flametalk.domain.feed.dto.FeedDetailResponse;
import com.devcamp.flametalk.domain.feed.dto.FeedsResponse;
import com.devcamp.flametalk.domain.feed.service.FeedService;
import com.devcamp.flametalk.global.common.CommonResponse;
import com.devcamp.flametalk.global.common.SingleDataResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
   * 프로필에 해당하는 피드를 모두 조회합니다.
   *
   * @param profileId    조회할 프로필 id
   * @param isBackground 배경사진 여부
   * @return 프로필 정보와 피드 리스트
   */
  @GetMapping
  public ResponseEntity<SingleDataResponse<FeedsResponse>> findFeeds(@RequestParam Long profileId,
      @RequestParam(required = false) Boolean isBackground) {

    // TODO: api gateway 적용 이후 header userId로 isLock 판별

    FeedsResponse feeds = feedService.findFeeds(profileId, isBackground);
    SingleDataResponse<FeedsResponse> response = new SingleDataResponse<>();
    response.success(FeedResponse.FEEDS_SUCCESS.getMessage(), feeds);
    log.info("read feeds {}", response.toString());
    return ResponseEntity.ok().body(response);
  }

  /**
   * 요청받은 id에 해당하는 피드 사진의 공개 여부를 반전시킵니다.
   *
   * @param feedId 피드 id
   * @return 공개 여부 결과에 따른 응답 정보와 업데이트된 피드 객체
   */
  @PutMapping("/lock/{feedId}")
  public ResponseEntity<SingleDataResponse<FeedDetailResponse>> updateLock(
      @PathVariable Long feedId) {
    FeedDetailResponse feedDetail = feedService.updateLock(feedId);
    log.info("update feed lock {}", feedDetail.toString());
    SingleDataResponse<FeedDetailResponse> response = new SingleDataResponse<>();
    response.success(FeedResponse.FEED_REVERSE_LOCK_SUCCESS.getMessage(), feedDetail);
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
