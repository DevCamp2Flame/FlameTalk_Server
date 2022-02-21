package com.devcamp.flametalk.chatroom.domain.chatroom.domain;

import com.devcamp.flametalk.chatroom.domain.file.domain.File;
import com.devcamp.flametalk.chatroom.domain.model.BaseTime;
import com.devcamp.flametalk.chatroom.domain.user.domain.User;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

/**
 * Chatroom 엔티티입니다.
 */
@Getter
@NoArgsConstructor
@Entity
public class Chatroom extends BaseTime {

  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(
      name = "UUID",
      strategy = "org.hibernate.id.UUIDGenerator"
  )
  @Column(length = 36)
  private String id;

  @NotNull
  private int count;

  @Column(nullable = false, columnDefinition = "TINYINT", length = 1)
  private Boolean isOpen;

  private String url;

  @ManyToOne
  @JoinColumn(name = "host_id")
  private User hostUser;

  @OneToMany(mappedBy = "chatroom")
  private List<UserChatroom> userChatrooms = new ArrayList<>();

  @OneToMany(mappedBy = "chatroom")
  private List<File> files = new ArrayList<>();

  /**
   * Chatroom 엔티티의 빌더입니다.
   *
   * @param id       채팅방 id
   * @param count    채팅방 인원 수
   * @param isOpen   오픈 채팅방 여부
   * @param url      오픈 채팅방의 url
   * @param hostUser 채팅방을 개설한 User
   */
  @Builder
  public Chatroom(String id, int count, boolean isOpen, String url, User hostUser) {
    this.id = id;
    this.count = count;
    this.isOpen = isOpen;
    this.url = url;
    this.hostUser = hostUser;
  }

  public void leave() {
    this.count--;
  }

  public void join() {
    this.count++;
  }
}
