package com.devcamp.flametalk.chatroom.domain.chatroom.domain;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Chatroom 엔티티의 저장소 Repository 입니다.
 */
public interface ChatroomRepository extends JpaRepository<Chatroom, String> {

}
