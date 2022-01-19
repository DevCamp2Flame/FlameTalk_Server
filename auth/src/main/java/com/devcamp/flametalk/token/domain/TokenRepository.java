package com.devcamp.flametalk.token.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Token 엔티티의 저장소입니다.
 */
public interface TokenRepository extends JpaRepository<Token, Long> {

  Optional<Token> findByDeviceId(String deviceId);
}