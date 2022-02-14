package com.devcamp.flametalk.chatroom.domain.chatroom.dto;

import com.devcamp.flametalk.chatroom.domain.chatroom.domain.Chatroom;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

/**
 * 채팅방 생성을 위한 클래스입니다.
 */
@AllArgsConstructor
@Getter
public class ChatroomCreateRequest {

  @NotNull
  private String hostId;

  private String hostOpenProfileId;

  @NotNull
  private Boolean isOpen;

  @NotNull
  private List<String> users;

  @Length(max = 50)
  private String title;

  private String thumbnail;

  /**
   * 인스턴스 정보를 Chatroom 엔티티로 반환하는 메소드입니다.
   *
   * @param url 오픈 채팅방인 경우의 url
   * @return Chatroom 엔티티
   */
  @Builder
  public Chatroom toChatroom(String url) {
    return Chatroom.builder()
        .count(users.size())
        .isOpen(isOpen)
        .url(url)
        .build();
  }
}
