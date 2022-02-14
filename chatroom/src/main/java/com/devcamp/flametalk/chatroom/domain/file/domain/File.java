package com.devcamp.flametalk.chatroom.domain.file.domain;

import com.devcamp.flametalk.chatroom.domain.chatroom.domain.Chatroom;
import com.devcamp.flametalk.chatroom.domain.model.BaseTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 파일 엔티티입니다.
 */
@Getter
@NoArgsConstructor
@Entity
public class File extends BaseTime {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  private String title;

  @NotNull
  private String extension;

  @NotNull
  private String url;

  @ManyToOne
  @JoinColumn(name = "chatroom_id")
  private Chatroom chatroom;

  /**
   * File 엔티티의 빌더입니다.
   *
   * @param title     파일명
   * @param extension 파일 확장자
   * @param url       파일의 S3 url
   * @param chatroom  파일이 사용된 채팅방
   */
  @Builder
  public File(String title, String extension, String url, Chatroom chatroom) {
    this.title = title;
    this.extension = extension;
    this.url = url;
    this.chatroom = chatroom;
  }
}
