package com.devcamp.flametalk.device;

import com.devcamp.flametalk.global.util.BaseTimeEntity;
import com.devcamp.flametalk.user.domain.User;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 사용자 기기 정보를 저장하는 엔티티입니다.
 */
@Getter
@Entity
@Table(name = "device")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Device extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(columnDefinition = "BIGINT")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  private User user;

  // 사용자 기기 고유 식별값
  @Column(nullable = false, columnDefinition = "VARCHAR(16)")
  private String deviceId;

  // 알림 발송을 위한 device token 정보
  @Column(columnDefinition = "VARCHAR(163)")
  private String token;

  // 해당 디바이스에서 마지막으로 읽은 메시지 id : curMaxMessageId 이후의 메세지만 알림 발송
  @Column(columnDefinition = "VARCHAR(45)")
  private String curMaxMessageId;

  /**
   * Device 객체를 만들기 위한 Builder 입니다.
   *
   * @param user            사용자
   * @param deviceId        사용자 기기 id
   * @param token           FCM에 등록된 기기 token 값
   * @param curMaxMessageId 디바이스에서 마지막으로 읽은 message id
   */
  @Builder
  public Device(User user, String deviceId, String token, String curMaxMessageId) {
    this.user = user;
    this.deviceId = deviceId;
    this.token = token;
    this.curMaxMessageId = curMaxMessageId;
  }
}