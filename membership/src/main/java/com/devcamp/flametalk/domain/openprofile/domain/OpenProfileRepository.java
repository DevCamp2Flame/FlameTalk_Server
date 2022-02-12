package com.devcamp.flametalk.domain.openprofile.domain;

import com.devcamp.flametalk.domain.user.domain.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * OpenProfile 엔티티의 저장소 Repository 입니다.
 */
public interface OpenProfileRepository extends JpaRepository<OpenProfile, Long> {

  List<OpenProfile> getAllByOpenProfileUser(User user);
}
