package com.devcamp.flametalk.chatroom.domain.chatroom.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 유저가 참여중인 채팅방 리스트 응답을 위한 클래스입니다.
 */
@AllArgsConstructor
@Getter
public class ChatroomsResponse {

  private String userId;
  private List<UserChatroomResponse> userChatrooms;

  public static ChatroomsResponse of(String userId, List<UserChatroomResponse> userChatrooms) {
    return new ChatroomsResponse(userId, userChatrooms);
  }
}
