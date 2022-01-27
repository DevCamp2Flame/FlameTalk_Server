package com.devcamp.flametalk.chatapi.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Chatroom extends BaseTimeEntity {

  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(
      name = "UUID",
      strategy = "org.hibernate.id.UUIDGenerator"
  )
  @Column(columnDefinition = "VARCHAR(36)")
  private String id;

  // todo : userId 와 연관관계 만들기
  @Column(nullable = false, columnDefinition = "VARCHAR(20)")
  private String hostId;

  @Column(nullable = false, columnDefinition = "INT")
  private Integer count;

  @Column(nullable = false, columnDefinition = "TINYINT", length = 1)
  private boolean isOpen;

  @OneToMany(mappedBy = "chatroom")
  private List<UserChatroom> userChatrooms = new ArrayList<>();
}
