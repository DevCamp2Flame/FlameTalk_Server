package com.devcamp.flametalk.domain.profile.dto;

import com.devcamp.flametalk.domain.user.domain.User;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 프로필 리스트 정보 응답을 위한 클래스입니다.
 */
@AllArgsConstructor
@Getter
public class ProfilesResponse {

  private String userId;
  private String nickname;
  private List<ProfileSimpleResponse> profiles;

  /**
   * User 엔티티와 단순 프로필 정보 리스트를 통해 유저 프로필 리스트 응답 객체를 생성하는 정적 팩토리 메소드입니다.
   *
   * @param user     User 엔티티
   * @param profiles 단순 프로필 정보 리스트
   * @return 유저 프로필 리스트
   */
  public static ProfilesResponse of(User user, List<ProfileSimpleResponse> profiles) {
    return new ProfilesResponse(
        user.getId(),
        user.getNickname(),
        profiles
    );
  }
}
