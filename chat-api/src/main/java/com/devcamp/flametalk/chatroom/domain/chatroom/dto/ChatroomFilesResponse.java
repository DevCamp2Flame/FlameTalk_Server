package com.devcamp.flametalk.chatroom.domain.chatroom.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 채팅방에 해당하는 파일 정보 리스트 응답을 위한 클래스입니다.
 */
@AllArgsConstructor
@Getter
public class ChatroomFilesResponse implements Comparable<ChatroomFilesResponse> {

  private Long id;
  private String url;
  private String title;
  private String extension;
  private LocalDateTime createdDate;

  @Override
  public int compareTo(ChatroomFilesResponse o) {
    return o.createdDate.compareTo(this.createdDate);
  }
}
