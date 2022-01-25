package com.devcamp.flametalk.common.domain;

import com.devcamp.flametalk.common.type.Social;
import com.devcamp.flametalk.common.type.Status;
import com.devcamp.flametalk.common.util.BaseTimeEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 사용자 엔티티입니다.
 */
@Getter
@Entity
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity implements UserDetails {

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(
      name = "uuid",
      strategy = "com.devcamp.flametalk.common.util.UuidGenerator"
  )
  @Column(nullable = false, columnDefinition = "VARCHAR(20)")
  private String id;

  @Column(nullable = false, unique = true, columnDefinition = "VARCHAR(320)")
  private String email;

  @JsonIgnore
  @Column(columnDefinition = "VARCHAR(60)")
  private String password;

  @Column(nullable = false, columnDefinition = "VARCHAR(20)")
  private String nickname;

  @Column(nullable = false, unique = true, columnDefinition = "VARCHAR(13)")
  private String phoneNumber;

  @Column(nullable = false, columnDefinition = "VARCHAR(10)")
  private String birthday;

  @Column(nullable = false, columnDefinition = "TINYINT")
  private Social social;

  @Column(nullable = false, columnDefinition = "TINYINT")
  private Status status;

  @Column(nullable = false, columnDefinition = "VARCHAR(2)")
  private String region;

  @Column(nullable = false, columnDefinition = "VARCHAR(3)")
  private String language;

  // 사용자 기기에서 가장 큰 curMaxMessageId 값 : 읽음 처리에 활용
  @Column(columnDefinition = "VARCHAR(45)")
  private String lastReadMessageId;

  // 해당 데이터를 테이블의 컬럼과 매핑 시키지 않는다.
  @Transient
  private Collection<SimpleGrantedAuthority> authorities;

  // todo : 이메일 인증을 위한 column

  /**
   * User 객체를 만들기 위한 Builder 입니다.
   *
   * @param nickname    별명
   * @param email       이메일
   * @param password    암호화된 비밀번호
   * @param status      상태
   * @param phoneNumber 휴대폰 번호
   * @param birthday    생년월일 'YYYY-MM-DD'
   * @param social      로그인 타입
   * @param region      국가
   * @param language    언어
   */
  @Builder
  public User(String nickname, String email, String password, Status status, String phoneNumber,
      String birthday, Social social, String region, String language) {
    this.nickname = nickname;
    this.email = email;
    this.password = password;
    this.status = status;
    this.phoneNumber = phoneNumber;
    this.birthday = birthday;
    this.social = social;
    this.region = region;
    this.language = language;
  }

  // todo : 권한 반환
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.authorities;
  }

  @Override
  public String getUsername() {
    return this.nickname;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  // todo : 이메일 인증 컬럼값에 따라 상태 변경
  public void activeStatus() {
    // 이메일 인증을 했다면 USER, 하지 않았다면 GUEST
    this.status = Status.USER;
  }

  public void inactiveStatus() {
    this.status = Status.LEAVE;
  }
}