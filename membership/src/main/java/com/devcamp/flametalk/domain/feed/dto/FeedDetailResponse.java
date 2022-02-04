package com.devcamp.flametalk.domain.feed.dto;

import com.devcamp.flametalk.domain.feed.domain.Feed;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 피드 상세 정보 응답을 위한 클래스입니다.
 */
@AllArgsConstructor
@Getter
public class FeedDetailResponse {

  private Long feedId;
  private String imageUrl;
  private Boolean isBackground;
  private Boolean isLock;
  private LocalDateTime createdDate;
  private LocalDateTime updatedDate;

  /**
   * Feed 리스트를 통해 FeedDetailResponse 리스트를 생성합니다.
   *
   * @param feeds Feed 리스트
   * @return 피드 상세 정보 리스트
   */
  public static List<FeedDetailResponse> createList(List<Feed> feeds) {
    List<FeedDetailResponse> feedDetails = new ArrayList<>();
    feeds.forEach(feed ->
        feedDetails.add(
            new FeedDetailResponse(feed.getId(), feed.getImageUrl(), feed.getIsBackground(),
                feed.getIsLock(), feed.getCreatedAt(), feed.getUpdatedAt())));
    return feedDetails;
  }
}
