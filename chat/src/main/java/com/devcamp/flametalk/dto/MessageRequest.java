package com.devcamp.flametalk.dto;

import com.devcamp.flametalk.domain.Message;
import com.fasterxml.uuid.Generators;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Websocket 채팅 메시지 송신 요청 객체입니다.
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MessageRequest {

  /**
   * MessageType 입니다.
   * INVITE(초대), ENTER(입장), TALK(대화), FILE(파일송신)
   */
  public enum MessageType {
    INVITE, ENTER, TALK, FILE
  }

  private MessageType type;
  private String room_id;
  private String sender_id;
  private String nickname;
  private String contents;
  private String file_url;

  /**
   * MessageRequest 객체를 Message 객체로 변환합니다.
   *
   * @return message
   */
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