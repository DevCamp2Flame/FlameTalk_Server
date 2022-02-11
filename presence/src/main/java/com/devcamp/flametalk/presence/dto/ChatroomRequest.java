package com.devcamp.flametalk.presence.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Websocket 채팅 입/퇴장 기록 요청 객체입니다.
 */
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ChatroomRequest {

  /**
   * 메시지 타입입니다.
   * ENTER(입장), EXIT(퇴장)
   */
  public enum Type {
    ENTER, EXIT
  }

  private Type type;
  private String userId;
  private String roomId;
  private String deviceId;
}