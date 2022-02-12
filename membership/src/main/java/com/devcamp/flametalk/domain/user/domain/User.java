package com.devcamp.flametalk.domain.user.domain;

import com.devcamp.flametalk.domain.friend.domain.Friend;
import com.devcamp.flametalk.domain.openprofile.domain.OpenProfile;
import com.devcamp.flametalk.domain.profile.domain.Profile;
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

  @OneToMany(mappedBy = "user")
  private List<Profile> profiles = new ArrayList<>();

  @OneToMany(mappedBy = "openProfileUser")
  private List<OpenProfile> openProfiles = new ArrayList<>();

  @OneToMany(mappedBy = "user")
  private List<Friend> users = new ArrayList<>();

  @OneToMany(mappedBy = "user")
  private List<Friend> friends = new ArrayList<>();
}
