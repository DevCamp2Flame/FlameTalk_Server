package com.devcamp.flametalk.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 채팅방 엔티티입니다.
 */
@Getter
@NoArgsConstructor
@Entity
public class Chatroom extends BaseTime {

  // TODO: 공통 모듈화 시 ID 수정 필요
  @Id
  private String id;

  // TODO: userId와 FK 연결 필요
  private String hostId;

  @NotNull
  private int count;

  @NotNull
  private boolean isOpen;

  @OneToMany(mappedBy = "chatroom")
  private List<File> files = new ArrayList<>();
}
