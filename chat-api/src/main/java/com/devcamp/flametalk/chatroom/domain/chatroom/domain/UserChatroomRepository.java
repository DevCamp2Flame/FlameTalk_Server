package com.devcamp.flametalk.chatroom.domain.chatroom.domain;

import com.devcamp.flametalk.chatroom.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserChatroomRepository extends JpaRepository<UserChatroom, String> {

  UserChatroom findByUserAndChatroom(User user, Chatroom chatroom);
}
