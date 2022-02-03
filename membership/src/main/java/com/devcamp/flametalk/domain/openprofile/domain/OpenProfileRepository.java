package com.devcamp.flametalk.domain.openprofile.domain;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * OpenProfile 엔티티의 저장소 Repository 입니다.
 */
public interface OpenProfileRepository extends JpaRepository<OpenProfile, Long> {

}
