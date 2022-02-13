package com.devcamp.flametalk.chatroom.domain.chatroom.dto;

import com.devcamp.flametalk.chatroom.domain.chatroom.domain.Chatroom;
import com.devcamp.flametalk.chatroom.domain.chatroom.domain.UserChatroom;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChatroomCreateResponse {

  private String chatroomId;
  private Long userChatroomId;
  private String hostId;
  private int count;
  private Boolean isOpen;
  private String url;
  private String title;
  private List<String> thumbnail;

  public static ChatroomCreateResponse of(UserChatroom userChatroom, Chatroom chatroom) {
    List<String> thumbnail = Optional.ofNullable(userChatroom.getImageUrl()).map(Arrays::asList)
        .orElse(null);

    return new ChatroomCreateResponse(
        chatroom.getId(),
        userChatroom.getId(),
        userChatroom.getUser().getId(),
        chatroom.getCount(),
        chatroom.getIsOpen(),
        chatroom.getUrl(),
        userChatroom.getTitle(),
        thumbnail
    );
  }

  public void updateDefaultThumbnail(List<String> defaultThumbnail) {
    this.thumbnail = defaultThumbnail;
  }

  public void updateDefaultTitle(String title) {
    this.title = title;
  }
}
