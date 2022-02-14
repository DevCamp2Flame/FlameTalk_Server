package com.devcamp.flametalk.chatroom.domain.chatroom.dto;

import com.devcamp.flametalk.chatroom.domain.profile.domain.Profile;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 유저 채팅방 단순 정보 응답을 위한 클래스입니다.
 */
@AllArgsConstructor
@Getter
public class ProfileSimpleResponse {

  private Long id;
  private String nickname;
  private String image;

  /**
   * Profile 리스트를 통해 ProfileSimpleResponse 리스트를 생성합니다.
   *
   * @param profiles Profile 리스트
   * @return ProfileSimpleResponse 리스트
   */
  public static List<ProfileSimpleResponse> createList(List<Profile> profiles) {
    List<ProfileSimpleResponse> simpleProfiles = new ArrayList<>();
    profiles.forEach(profile -> simpleProfiles.add(
        new ProfileSimpleResponse(profile.getId(), profile.getUser().getNickname(),
            profile.getImageUrl())));
    return simpleProfiles;
  }
}
