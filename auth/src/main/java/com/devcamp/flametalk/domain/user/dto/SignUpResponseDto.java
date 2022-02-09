package com.devcamp.flametalk.domain.user.dto;

import com.devcamp.flametalk.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 회원가입 응답 Dto 입니다.
 */
@NoArgsConstructor
@Getter
public class SignUpResponseDto {

  private String userId;
  private String email;
  private String nickname;
  private String phoneNumber;
  private String birthday;
  private String social;
  private String region;
  private String language;

  /**
   * User 에서 SignUpResponseDto 로 변환하는 메소드입니다.
   *
   * @param user 사용자
   */
  @Builder
  public SignUpResponseDto(User user) {
    this.userId = user.getId();
    this.email = user.getEmail();
    this.nickname = user.getNickname();
    this.phoneNumber = user.getPhoneNumber();
    this.birthday = user.getBirthday();
    this.social = user.getSocial().getName();
    this.region = user.getRegion();
    this.language = user.getLanguage();
  }
}