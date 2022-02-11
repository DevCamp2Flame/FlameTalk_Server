package com.devcamp.flametalk.domain.profile;

import com.devcamp.flametalk.domain.user.domain.User;
import com.devcamp.flametalk.global.util.BaseTimeEntity;
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
 * Profile 엔티티 입니다.
 */
@Getter
@NoArgsConstructor
@Entity
public class Profile extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String imageUrl;

  private String bgImageUrl;

  @Column(columnDefinition = "VARCHAR(60)")
  private String description;

  private String sticker;

  @Column(nullable = false, columnDefinition = "TINYINT", length = 1)
  private Boolean isDefault;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @Builder
  public Profile(Boolean isDefault, User user) {
    this.sticker = "[]";
    this.isDefault = isDefault;
    this.user = user;
  }
}