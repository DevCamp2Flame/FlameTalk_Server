package com.devcamp.flametalk.chatroom.domain.user;

import com.devcamp.flametalk.chatroom.domain.chatroom.domain.Chatroom;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * User 엔티티 입니다.
 */
@Getter
@NoArgsConstructor
@Entity
public class User {

  @Id
  private String id;

  @NotNull
  private String email;

  private String password;

  @NotNull
  private String nickname;

  @NotNull
  private String phoneNumber;

  @NotNull
  private String birthday;

  @NotNull
  private short social;

  @NotNull
  private short status;

  @NotNull
  private String region;

  @NotNull
  private String language;

  @OneToMany(mappedBy = "hostUser")
  private List<Chatroom> chatrooms = new ArrayList<>();
}
