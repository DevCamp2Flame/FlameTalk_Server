package com.devcamp.flametalk.domain.feed.domain;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Feed 엔티티의 저장소 Repository 입니다.
 */
public interface FeedRepository extends JpaRepository<Feed, Long> {

}
