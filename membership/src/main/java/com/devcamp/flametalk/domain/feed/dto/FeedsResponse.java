package com.devcamp.flametalk.domain.feed.dto;

import com.devcamp.flametalk.domain.profile.domain.Profile;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 피드 리스트 정보 응답을 위한 클래스입니다.
 */
@AllArgsConstructor
@Getter
public class FeedsResponse {

  private Long profileId;
  private String nickname;
  private String profileImage;
  private List<FeedDetailResponse> feeds;

  /**
   * Profile 엔티티와 피드 상세 정보 리스트를 통해 피드 리스트 응답 객체를 생성하는 정적 팩토리 메소드입니다.
   *
   * @param profile Profile 엔티티
   * @param feeds 피드 상세 정보 리스트
   * @return 피드 리스트 응답 객체
   */
  public static FeedsResponse of(Profile profile, List<FeedDetailResponse> feeds) {
    return new FeedsResponse(
        profile.getId(),
        profile.getUser().getNickname(),
        profile.getImageUrl(),
        feeds
    );
  }
}
