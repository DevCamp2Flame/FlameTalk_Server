package com.devcamp.flametalk.chatroom.domain.chatroom.dto;

import com.devcamp.flametalk.chatroom.domain.chatroom.domain.UserChatroom;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 유저가 참여중인 채팅방 일부 정보 응답을 위한 클래스입니다.
 */
@AllArgsConstructor
@Getter
public class UserChatroomResponse {

  private String chatroomId;
  private Long userChatroomId;
  private String title;
  private List<String> thumbnail;
  private String lastReadMessage;
  private Boolean inputLock;
  private int count;

  /**
   * UserChatroom 엔티티를 UserChatroomResponse 객체로 생성하는 정적 팩토리 메소드입니다.
   *
   * @param userChatroom UserChatroom 엔티티
   * @return UserChatroomResponse 객체
   */
  public static UserChatroomResponse of(UserChatroom userChatroom) {
    List<String> thumbnail = Optional.ofNullable(userChatroom.getImageUrl()).map(Arrays::asList)
        .orElse(null);
    return new UserChatroomResponse(
        userChatroom.getChatroom().getId(),
        userChatroom.getId(),
        userChatroom.getTitle(),
        thumbnail,
        userChatroom.getLastReadMessageId(),
        userChatroom.getInputLock(),
        userChatroom.getChatroom().getCount()
    );
  }

  public void updateDefaultThumbnail(List<String> defaultThumbnail) {
    this.thumbnail = defaultThumbnail;
  }

  public void updateDefaultTitle(String title) {
    this.title = title;
  }
}
