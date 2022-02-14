package com.devcamp.flametalk.chatroom.domain.chatroom.dto;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserChatroomCreateRequest {

  @NotNull
  private String chatroomId;

  @NotNull
  private String userId;

  private Long openProfileId;

}
