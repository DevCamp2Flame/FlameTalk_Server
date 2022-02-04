package com.devcamp.flametalk.domain.feed.domain;

import com.devcamp.flametalk.domain.profile.domain.Profile;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Feed 엔티티의 저장소 Repository 입니다.
 */
public interface FeedRepository extends JpaRepository<Feed, Long> {

  List<Feed> getAllByProfile(Profile profile);

  List<Feed> getAllByProfileAndIsBackground(Profile profile, Boolean isBackground);
}
