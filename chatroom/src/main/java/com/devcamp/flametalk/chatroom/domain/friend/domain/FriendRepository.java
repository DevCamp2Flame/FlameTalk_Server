package com.devcamp.flametalk.chatroom.domain.friend.domain;

import com.devcamp.flametalk.chatroom.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Friend 엔티티의 저장소 Repository 입니다.
 */
public interface FriendRepository extends JpaRepository<Friend, Long> {

  boolean existsByUserAndFriendUser(User user, User friendUser);

  Friend findByUserAndFriendUser(User user, User friendUser);
}
