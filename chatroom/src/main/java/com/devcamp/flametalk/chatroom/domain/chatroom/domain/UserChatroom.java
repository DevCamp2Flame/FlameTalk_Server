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

/**
 * UserChatroom 엔티티입니다.
 */
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

  /**
   * UserChatroom 엔티티의 빌더입니다.
   *
   * @param id                유저 채팅방 id
   * @param title             유저가 설정한 채팅방의 제목
   * @param lastReadMessageId 유저가 채팅방에서 마지막으로 확인한 채팅 메세지
   * @param imageUrl          유저가 설정한 채팅방의 썸네일 이미지
   * @param inputLock         유저의 채팅방 잠금 여부
   * @param chatroom          유저 채팅방이 참조하는 채팅방
   * @param user              유저 채팅방에 해당하는 유저
   */
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

  /**
   * UserChatroom 엔티티를 업데이트합니다.
   *
   * @param updateUserChatroom 업데이트될 유저 채팅방
   * @return 업데이트된 유저 채팅방
   */
  public UserChatroom update(UserChatroom updateUserChatroom) {
    this.title = updateUserChatroom.title;
    this.lastReadMessageId = updateUserChatroom.lastReadMessageId;
    this.imageUrl = updateUserChatroom.imageUrl;
    this.inputLock = updateUserChatroom.inputLock;

    return this;
  }

  public void close(String lastReadMessageId) {
    this.lastReadMessageId = lastReadMessageId;
  }
}
