package com.devcamp.flametalk.dto;

import com.devcamp.flametalk.domain.Message;
import com.fasterxml.uuid.Generators;
import java.time.LocalDateTime;
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

  public Message toEntity() {
    return Message.builder()
        .message_id(Generators.timeBasedGenerator().generate().toString())
        .sender_id(this.sender_id)
        .room_id(this.room_id)
        .contents(this.contents)
        .created_at(LocalDateTime.now())
        .build();
  }
}