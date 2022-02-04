package com.devcamp.flametalk.domain.feed.service;

import static com.devcamp.flametalk.global.error.ErrorCode.FEED_NOT_FOUND;
import static com.devcamp.flametalk.global.error.ErrorCode.PROFILE_NOT_FOUND;

import com.devcamp.flametalk.domain.feed.domain.Feed;
import com.devcamp.flametalk.domain.feed.domain.FeedRepository;
import com.devcamp.flametalk.domain.feed.dto.FeedDetailResponse;
import com.devcamp.flametalk.domain.feed.dto.FeedsResponse;
import com.devcamp.flametalk.domain.profile.domain.Profile;
import com.devcamp.flametalk.domain.profile.domain.ProfileRepository;
import com.devcamp.flametalk.global.error.exception.EntityNotFoundException;
import java.util.List;
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
  private final ProfileRepository profileRepository;

  /**
   * 프로필 id에 해당하는 요청된 피드 리스트를 조회합니다.
   *
   * @param profileId    프로필 id
   * @param isBackground 배경사진 리스트 요청 여부
   * @return 피드 리스트
   */
  public FeedsResponse findFeeds(Long profileId, Boolean isBackground) {
    Profile profile = profileRepository.findById(profileId)
        .orElseThrow(() -> new EntityNotFoundException(PROFILE_NOT_FOUND));

    List<Feed> feeds;
    if (isBackground == null) {
      feeds = feedRepository.getAllByProfile(profile);
    } else {
      feeds = feedRepository.getAllByProfileAndIsBackground(profile, isBackground);
    }
    return FeedsResponse.of(profile, FeedDetailResponse.createList(feeds));
  }

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

  /**
   * id에 해당하는 피드 사진의 공개여부를 업데이트합니다.
   *
   * @param id 피드 id
   */
  @Transactional
  public void updateLock(Long id) {
    Feed feed = feedRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(FEED_NOT_FOUND));
    feed.reverseLock();
  }
}
