package com.devcamp.flametalk.chatroom.domain.file.domain;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * File 엔티티의 저장소 Repository 입니다.
 */
public interface FileRepository extends JpaRepository<File, Long> {

}
