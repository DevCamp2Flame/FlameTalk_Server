package com.devcamp.flametalk.chatroom.domain.chatroom.dto;

import com.devcamp.flametalk.chatroom.domain.chatroom.domain.Chatroom;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ChatroomCreateRequest {

  @NotNull
  private String hostId;

  @NotNull
  private Boolean isOpen;

  @NotNull
  private List<String> users;

  @NotNull
  private String lastReadMessageId;

  private String title;

  private String thumbnail;

  public Chatroom toChatroom() {
    return Chatroom.builder()
        .;
  }
}
