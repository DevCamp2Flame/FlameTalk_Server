package com.devcamp.flametalk.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

  private String chatroomId;

  /**
   * File 엔티티의 빌더입니다.
   *
   * @param title      파일명
   * @param extension  파일 확장자
   * @param url        파일의 S3 url
   * @param chatroomId 채팅방에서 사용된 파일의 경우, 채팅방의 id
   */
  @Builder
  public File(String title, String extension, String url, String chatroomId) {
    this.title = title;
    this.extension = extension;
    this.url = url;
    this.chatroomId = chatroomId;
  }
}
