package com.devcamp.flametalk.chatroom.domain.chatroom.dto;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 유저 채팅방의 마지막 메세지 갱신을 위한 클래스입니다.
 */
@AllArgsConstructor
@Getter
public class UserChatroomCloseRequest {

  @NotNull
  private Long userChatroomId;

  @NotNull
  private String lastReadMessageId;
}
