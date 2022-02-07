package com.devcamp.flametalk.chatroom.domain.chatroom.dto;

import com.devcamp.flametalk.chatroom.domain.chatroom.domain.UserChatroom;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

/**
 * 유저 채팅방 업데이트를 위한 클래스입니다.
 */
@AllArgsConstructor
@Getter
public class UserChatroomUpdateRequest {

  @NotNull
  private Boolean inputLock;

  @Length(max = 50)
  private String title;

  private String imageUrl;

  /**
   * 인스턴스 정보를 UserChatroom 엔티티로 반환하는 메소드입니다.
   *
   * @param userChatroom 기존 UserChatroom 엔티티
   * @return 업데이트된 UserChatroom 엔티티
   */
  public UserChatroom toUserChatroom(UserChatroom userChatroom) {
    return UserChatroom.builder()
        .title(title)
        .imageUrl(imageUrl)
        .inputLock(inputLock)
        .chatroom(userChatroom.getChatroom())
        .user(userChatroom.getUser())
        .build();
  }
}
