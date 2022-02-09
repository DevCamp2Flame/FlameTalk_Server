package com.devcamp.flametalk.domain.user.dto;

import com.devcamp.flametalk.domain.user.domain.Social;
import com.devcamp.flametalk.domain.user.domain.Status;
import com.devcamp.flametalk.domain.user.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 회원가입 요청 Dto 입니다.
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignUpRequestDto {

  private String email;
  private String password;
  private String nickname;
  private String phoneNumber;
  private String birthday;
  private String social;
  private String region;
  private String language;
  private String deviceId;

  /**
   * SignUpRequestDto 에서 User 로 변환하는 메소드입니다.
   *
   * @param encodedPassword 암호화된 비밀번호
   * @return 회원가입 완료된 사용자 정보를 반환합니다.
   */
  public User toEntity(String encodedPassword) {
    return User.builder()
        .email(this.email)
        .password(encodedPassword)
        .nickname(this.nickname)
        .phoneNumber(this.phoneNumber)
        .birthday(this.birthday)
        .social(Social.stringToSocial(getSocial()))
        .status(Status.USER)
        .region(this.region)
        .language(this.language)
        .build();
  }
}