package com.devcamp.flametalk.chatroom.domain.chatroom.dto;

import com.devcamp.flametalk.chatroom.domain.chatroom.domain.Chatroom;
import com.devcamp.flametalk.chatroom.domain.chatroom.domain.UserChatroom;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 입장한 채팅방 정보 응답을 위한 클래스입니다.
 */
@AllArgsConstructor
@Getter
public class JoinChatroomResponse {

  private String chatroomId;
  private Long userChatroomId;
  private Long openProfileId;
  private int count;
  private Boolean isOpen;
  private String title;
  private List<String> thumbnail;

  /**
   * JoinChatroomResponse 객체로 생성하는 정적 팩토리 메소드입니다.
   *
   * @param chatroom     입장하는 채팅방 Entity
   * @param userChatroom 생성된 유저 채팅방 Entity
   * @param title        채팅방 이름
   * @param thumbnail    채팅방 썸네일
   * @return JoinChatroomResponse 객체
   */
  public static JoinChatroomResponse of(Chatroom chatroom, UserChatroom userChatroom, String title,
      List<String> thumbnail) {
    return new JoinChatroomResponse(
        chatroom.getId(),
        userChatroom.getId(),
        null,
        chatroom.getCount(),
        chatroom.getIsOpen(),
        title,
        thumbnail
    );
  }
}
