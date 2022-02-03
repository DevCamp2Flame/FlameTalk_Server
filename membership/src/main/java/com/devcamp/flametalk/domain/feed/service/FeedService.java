package com.devcamp.flametalk.domain.feed.service;

import static com.devcamp.flametalk.global.error.ErrorCode.FEED_NOT_FOUND;

import com.devcamp.flametalk.domain.feed.domain.Feed;
import com.devcamp.flametalk.domain.feed.domain.FeedRepository;
import com.devcamp.flametalk.global.error.exception.EntityNotFoundException;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Feed API 로직을 관리하는 Service 입니다.
 */
@RequiredArgsConstructor
@Service
public class FeedService {

  private final FeedRepository feedRepository;

  /**
   * id에 해당하는 피드를 삭제합니다.
   *
   * @param id 피드 id
   */
  @Transactional
  public void deleteById(Long id) {
    Feed feed = feedRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(FEED_NOT_FOUND));
    feedRepository.delete(feed);
  }
}
