package com.devcamp.flametalk.chatapi.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatroomRepository extends JpaRepository<Chatroom, String> {

  // 채팅방 생성순서 최근 순으로 반환

  // findRoomById
}
