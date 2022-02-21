package com.devcamp.flametalk.domain.profile.dto;

import com.devcamp.flametalk.domain.profile.domain.Profile;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 단순 프로필 정보 응답을 위한 클래스입니다.
 */
@AllArgsConstructor
@Getter
public class ProfileSimpleResponse {

  private Long id;
  private String imageUrl;
  private String description;
  private Boolean isDefault;

  private static ProfileSimpleResponse from(Profile profile) {
    return new ProfileSimpleResponse(
        profile.getId(),
        profile.getImageUrl(),
        profile.getDescription(),
        profile.getIsDefault()
    );
  }

  /**
   * Profile 리스트를 통해 ProfileSimpleResponse 리스트를 생성합니다.
   *
   * @param profiles Profile 리스트
   * @return ProfileSimpleResponse 리스트
   */
  public static List<ProfileSimpleResponse> createList(List<Profile> profiles) {
    List<ProfileSimpleResponse> profileDetails = new ArrayList<>();
    profiles.forEach(
        profile -> profileDetails.add(ProfileSimpleResponse.from(profile))
    );
    return profileDetails;
  }
}
