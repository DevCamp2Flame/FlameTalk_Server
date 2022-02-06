package com.devcamp.flametalk.chatroom.domain.profile.domain;

import com.devcamp.flametalk.chatroom.domain.user.domain.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Profile 엔티티의 저장소 Repository 입니다.
 */
public interface ProfileRepository extends JpaRepository<Profile, Long> {

  Profile findByUserAndIsDefault(User user, Boolean isDefault);

  List<Profile> findAllByUser(User user);
}
