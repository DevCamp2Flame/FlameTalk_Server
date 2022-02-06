package com.devcamp.flametalk.chatroom.domain.chatroom.domain;

import com.devcamp.flametalk.chatroom.domain.model.BaseTime;
import com.devcamp.flametalk.chatroom.domain.user.domain.User;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class UserChatroom extends BaseTime {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 50)
  private String title;

  @Column(length = 45)
  private String lastReadMessageId;

  private String imageUrl;

  @Column(nullable = false, columnDefinition = "TINYINT", length = 1)
  private Boolean inputLock;

  @ManyToOne
  @JoinColumn(name = "chatroom_id")
  private Chatroom chatroom;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @Builder
  public UserChatroom(Long id, String title, String lastReadMessageId, String imageUrl,
      Boolean inputLock, Chatroom chatroom, User user) {
    this.id = id;
    this.title = title;
    this.lastReadMessageId = lastReadMessageId;
    this.imageUrl = imageUrl;
    this.inputLock = inputLock;
    this.chatroom = chatroom;
    this.user = user;
  }
}
