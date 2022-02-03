package com.devcamp.flametalk.domain.openprofile.domain;

import com.devcamp.flametalk.domain.model.BaseTime;
import com.devcamp.flametalk.domain.user.domain.User;
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

/**
 * OpenProfile 엔티티 입니다.
 */
@Getter
@NoArgsConstructor
@Entity
public class OpenProfile extends BaseTime {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @Column(length = 20)
  private String nickname;

  private String imageUrl;

  @Column(length = 60)
  private String description;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User openProfileUser;
}
