package com.devcamp.flametalk.chatroom.domain.chatroom.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChatroomCreateResponse {

  private String roomId;
  private String hostId;
  private String title;
  private Boolean isOpen;
}