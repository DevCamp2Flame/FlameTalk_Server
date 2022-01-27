package com.devcamp.flametalk.chatapi.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserChatroom extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(columnDefinition = "BIGINT")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "chatroom_id")
  private Chatroom chatroom;

  // todo : ManyToOne user 로 변경
  @Column(nullable = false, columnDefinition = "VARCHAR(20)")
  private String userId;

  @Column(nullable = false, columnDefinition = "VARCHAR(50)")
  private String title;

  @Column(columnDefinition = "VARCHAR(45)")
  private String lastReadMessageId;

  @Column(columnDefinition = "BIGINT")
  private Long imageId;

  @Column(nullable = false, columnDefinition = "TINYINT(1)")
  private Boolean inputLock;
}
