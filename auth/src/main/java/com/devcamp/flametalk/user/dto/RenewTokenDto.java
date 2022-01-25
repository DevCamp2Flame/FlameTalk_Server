package com.devcamp.flametalk.user.dto;

import com.devcamp.flametalk.common.domain.User;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;

/**
 * 토큰 재발급 Dto 입니다.
 */
@Getter
public class RenewTokenDto {

  private String userId;
  private String nickname;
  private String status;
  private String accessToken;
  private String refreshToken;

  /**
   * 사용자 정보, 토큰 정보를 담아 갱신된 토큰 객체를 생성합니다.
   *
   * @param user   사용자
   * @param tokens 토큰
   */
  @Builder
  public RenewTokenDto(User user, Map<String, String> tokens) {
    this.userId = user.getId();
    this.nickname = user.getNickname();
    this.status = user.getStatus().getName();
    this.accessToken = tokens.get("access_token");
    this.refreshToken = tokens.get("refresh_token");
  }
}
