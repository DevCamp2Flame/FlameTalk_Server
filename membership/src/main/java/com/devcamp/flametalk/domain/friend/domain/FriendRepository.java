package com.devcamp.flametalk.domain.friend.domain;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Friend 엔티티의 저장소 Repository 입니다.
 */
public interface FriendRepository extends JpaRepository<Friend, Long> {

}
