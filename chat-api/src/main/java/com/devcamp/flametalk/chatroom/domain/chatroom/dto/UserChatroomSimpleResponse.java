package com.devcamp.flametalk.chatroom.domain.chatroom.dto;

import com.devcamp.flametalk.chatroom.domain.chatroom.domain.UserChatroom;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 유저 채팅방 단순 정보 응답을 위한 클래스입니다.
 */
@AllArgsConstructor
@Getter
public class UserChatroomSimpleResponse {

  private Long userChatroomId;
  private String title;
  private List<String> thumbnail;
  private Boolean inputLock;

  /**
   * UserChatroom 엔티티를 응답 객체로 생성하는 정적 팩토리 메소드입니다.
   *
   * @param userChatroom UserChatroom 엔티티
   * @return 유저 채팅방 단순 응답 객체
   */
  public static UserChatroomSimpleResponse from(UserChatroom userChatroom) {
    List<String> thumbnail = Optional.ofNullable(userChatroom.getImageUrl()).map(Arrays::asList)
        .orElse(null);
    return new UserChatroomSimpleResponse(
        userChatroom.getId(),
        userChatroom.getTitle(),
        thumbnail,
        userChatroom.getInputLock()
    );
  }

  public void updateDefaultThumbnail(List<String> defaultThumbnail) {
    this.thumbnail = defaultThumbnail;
  }

  public void updateDefaultTitle(String title) {
    this.title = title;
  }
}
