package com.devcamp.flametalk.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MessageRequest {

  public enum MessageType {
    ENTER, TALK
  }
  private MessageType type;
  private String room_id;
  private String sender_id;
  private String nickname;
  private String contents;
}
