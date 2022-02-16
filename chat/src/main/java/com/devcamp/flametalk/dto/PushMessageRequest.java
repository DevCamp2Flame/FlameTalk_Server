package com.devcamp.flametalk.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * FCM push 알림을 보내기 위한 요청 객체입니다.
 */
@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
@ToString
public class PushMessageRequest {

  private List<String> user_list;
  private String message_id;
  private String room_id;
  private String sender_id;
  private String nickname;
  private String contents;
  private String file_url;
}
