package com.devcamp.flametalk.chatroom.domain.chatroom.domain;

import com.devcamp.flametalk.chatroom.domain.user.domain.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * UserChatroom 엔티티의 저장소 Repository 입니다.
 */
public interface UserChatroomRepository extends JpaRepository<UserChatroom, Long> {

  UserChatroom findByUserAndChatroom(User user, Chatroom chatroom);

  List<UserChatroom> findAllByChatroom(Chatroom chatroom);

  List<UserChatroom> findAllByUser(User user);
}
