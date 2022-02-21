package com.devcamp.flametalk.domain.user.dto;

import com.devcamp.flametalk.domain.user.domain.User;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 로그인 응답 Dto 입니다.
 */
@NoArgsConstructor
@Getter
public class SignInResponseDto {

  private String userId;
  private String nickname;
  private String status;
  private String accessToken;
  private String refreshToken;

  /**
   * User 에서 SignUpResponseDto 로 변환하는 메소드입니다.
   *
   * @param user   사용자
   * @param tokens 토큰
   */
  @Builder
  public SignInResponseDto(User user, Map<String, String> tokens) {
    this.userId = user.getId();
    this.nickname = user.getNickname();
    this.status = user.getStatus().getName();
    this.accessToken = tokens.get("access_token");
    this.refreshToken = tokens.get("refresh_token");
  }
}