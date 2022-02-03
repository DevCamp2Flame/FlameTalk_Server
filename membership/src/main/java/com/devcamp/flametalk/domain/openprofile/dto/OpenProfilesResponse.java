package com.devcamp.flametalk.domain.openprofile.dto;

import com.devcamp.flametalk.domain.user.domain.User;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 오픈 프로필 리스트 정보 응답을 위한 클래스입니다.
 */
@AllArgsConstructor
@Getter
public class OpenProfilesResponse {

  private String userId;
  private List<OpenProfileDetailResponse> openProfiles;

  /**
   * User 엔티티와 오픈 프로필 상세 정보 리스트를 통해 오픈 프로필 리스트 응답 객체를 생성하는 정적 팩토리 메소드입니다.
   *
   * @param user         User 엔티티
   * @param openProfiles 오픈 프로필 상세 정보 리스트
   * @return 오픈 프로필 리스트 응답 객체
   */
  public static OpenProfilesResponse of(User user, List<OpenProfileDetailResponse> openProfiles) {
    return new OpenProfilesResponse(
        user.getId(),
        openProfiles
    );
  }
}
