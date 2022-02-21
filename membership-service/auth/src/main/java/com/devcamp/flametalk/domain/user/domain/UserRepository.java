package com.devcamp.flametalk.domain.user.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * User 엔티티의 저장소입니다.
 */
public interface UserRepository extends JpaRepository<User, String> {

  Optional<User> findById(String userId);

  Optional<User> findByEmail(String email);

  Optional<User> findByPhoneNumber(String phoneNumber);
}