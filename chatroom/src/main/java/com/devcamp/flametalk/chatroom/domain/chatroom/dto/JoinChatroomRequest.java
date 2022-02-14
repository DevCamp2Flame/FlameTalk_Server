package com.devcamp.flametalk.chatroom.domain.chatroom.dto;

import com.devcamp.flametalk.chatroom.domain.chatroom.domain.Chatroom;
import com.devcamp.flametalk.chatroom.domain.chatroom.domain.UserChatroom;
import com.devcamp.flametalk.chatroom.domain.user.domain.User;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 기존 채팅방 입장을 위한 클래스입니다.
 */
@AllArgsConstructor
@Getter
public class JoinChatroomRequest {

  @NotNull
  private String chatroomId;

  @NotNull
  private String userId;

  private Long openProfileId;

  /**
   * 인스턴스 정보를 UserChatroom 엔티티로 반환하는 메소드입니다.
   *
   * @param chatroom 입장하는 Chatroom 엔티티
   * @param user     채팅방에 입장하는 User 엔티티
   * @return UserChatroom 엔티티
   */
  public UserChatroom toUserChatroom(Chatroom chatroom, User user) {
    return UserChatroom.builder()
        .title(null)
        .lastReadMessageId(null)
        .imageUrl(null)
        .inputLock(false)
        .chatroom(chatroom)
        .user(user)
        .build();
  }
}
