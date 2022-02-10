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
    INVITE, ENTER, TALK, FILE
  }

  private MessageType type;
  private String room_id;
  private String sender_id;
  private String nickname;
  private String contents;
  private String file_url;

  public Message toEntity() {
    return Message.builder()
        .message_id(Generators.timeBasedGenerator().generate().toString())
        .message_type(this.type.name())
        .sender_id(this.sender_id)
        .nickname(this.nickname)
        .room_id(this.room_id)
        .contents(this.contents)
        .file_url(file_url)
        .created_at(LocalDateTime.now())
        .build();
  }
}