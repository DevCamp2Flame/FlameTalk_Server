package com.devcamp.flametalk.domain.friend.domain;

import com.devcamp.flametalk.domain.model.BaseTime;
import com.devcamp.flametalk.domain.profile.domain.Profile;
import com.devcamp.flametalk.domain.user.domain.User;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Friend 엔티티 입니다.
 */
@Getter
@NoArgsConstructor
@Entity
public class Friend extends BaseTime {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Convert(converter = PreviewConverter.class)
  private Preview preview;

  @NotNull
  private boolean isMarked;

  @NotNull
  private boolean isHidden;

  @NotNull
  private boolean isBlocked;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne
  @JoinColumn(name = "friend_id")
  private User userFriend;

  @ManyToOne
  @JoinColumn(name = "assigned_profile_id")
  private Profile profile;
}
