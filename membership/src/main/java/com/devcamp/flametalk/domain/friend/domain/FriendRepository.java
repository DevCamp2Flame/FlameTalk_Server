package com.devcamp.flametalk.domain.friend.domain;

import com.devcamp.flametalk.domain.profile.domain.Profile;
import com.devcamp.flametalk.domain.user.domain.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Friend 엔티티의 저장소 Repository 입니다.
 */
public interface FriendRepository extends JpaRepository<Friend, Long> {

  Optional<Friend> findByUserAndUserFriend(User user, User friend);

  boolean existsByUserAndUserFriend(User user, User friend);

  List<Friend> findAllByUserAndIsMarked(User user, Boolean isMarked);

  List<Friend> findAllByUserAndIsBlockedAndIsHidden(User user, Boolean isBlocked, Boolean isHidden);

  List<Friend> findAllByProfile(Profile profile);
}
