package com.devcamp.flametalk.domain.profile.domain;

import com.devcamp.flametalk.domain.user.domain.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Profile 엔티티의 저장소 Repository 입니다.
 */
public interface ProfileRepository extends JpaRepository<Profile, Long> {

  List<Profile> getAllByUser(User user);
}
