package com.devcamp.flametalk.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
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
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @NotNull
  private String title;

  @NotNull
  private String extension;

  @NotNull
  private String url;

  private String chatroomId;
}
