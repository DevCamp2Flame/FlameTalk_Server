package com.devcamp.flametalk.chatapi.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatroomRepository extends JpaRepository<Chatroom, String> {

}
