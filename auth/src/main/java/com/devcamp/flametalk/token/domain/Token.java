package com.devcamp.flametalk.token.domain;

import com.devcamp.flametalk.common.util.BaseTimeEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 사용자 토큰 정보를 저장하는 엔티티입니다.
 */
@Getter
@Entity
@Table(name = "token")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Token extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(columnDefinition = "BIGINT")
  private Long id;

  @Column(nullable = false, columnDefinition = "VARCHAR(20)")
  private String userId;

  @Column(nullable = false, columnDefinition = "VARCHAR(16)")
  private String deviceId;

  @Column(columnDefinition = "VARCHAR(244)")
  private String accessToken;

  @Column(columnDefinition = "VARCHAR(244)")
  private String refreshToken;

  /**
   * Token 객체를 만들기 위한 Builder 입니다.
   *
   * @param id           Token 엔티티의 id
   * @param userId       사용자 id
   * @param deviceId     사용자 기기의 id
   * @param accessToken  accessToken 만료 기간 1시간
   * @param refreshToken refreshToken 만료 기간 2주
   */
  @Builder
  public Token(Long id, String userId, String deviceId, String accessToken,
      String refreshToken) {
    this.id = id;
    this.userId = userId;
    this.deviceId = deviceId;
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
  }

  public void setToken(String accessToken, String refreshToken) {
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
  }
}