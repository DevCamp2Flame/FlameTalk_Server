package com.devcamp.flametalk.presence.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ChatroomRequest {

  public enum Type {
    ENTER, EXIT
  }

  private Type type;
  private String userId;
  private String roomId;
  private String deviceId;
}