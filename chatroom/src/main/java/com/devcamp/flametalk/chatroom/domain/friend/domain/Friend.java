package com.devcamp.flametalk.chatroom.domain.friend.domain;

import com.devcamp.flametalk.chatroom.domain.model.BaseTime;
import com.devcamp.flametalk.chatroom.domain.profile.domain.Profile;
import com.devcamp.flametalk.chatroom.domain.user.domain.User;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
/**
 * Friend 엔티티 입니다.
 */
public class Friend extends BaseTime {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  private String preview;

  @Column(nullable = false, columnDefinition = "TINYINT", length = 1)
  private boolean isMarked;

  @Column(nullable = false, columnDefinition = "TINYINT", length = 1)
  private boolean isHidden;

  @Column(nullable = false, columnDefinition = "TINYINT", length = 1)
  private boolean isBlocked;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne
  @JoinColumn(name = "friend_id")
  private User friendUser;

  @ManyToOne
  @JoinColumn(name = "assigned_profile_id")
  private Profile assignedProfile;
}
