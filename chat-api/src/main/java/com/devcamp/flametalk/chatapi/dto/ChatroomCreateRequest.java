package com.devcamp.flametalk.chatapi.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ChatroomCreateRequest {

  private String hostId;
  private Boolean isOpen;

  // todo : string -> user로 대체
  private List<String> users;
}
