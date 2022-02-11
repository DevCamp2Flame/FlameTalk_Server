package com.devcamp.flametalk.domain.openprofile.dto;

import com.devcamp.flametalk.domain.openprofile.domain.OpenProfile;
import com.devcamp.flametalk.domain.user.domain.User;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

/**
 * 오픈 프로필 수정을 위한 클래스입니다.
 */
@AllArgsConstructor
@Getter
public class OpenProfileUpdateRequest {

  @NotNull
  @Length(max = 20)
  private String nickname;

  private String imageUrl;

  @Length(max = 60)
  private String description;

  /**
   * 인스턴스 정보를 OpenProfile 엔티티로 반환하는 메소드입니다.
   *
   * @param user 기존 오픈 프로필에게 할당된 user
   * @return OpenProfile 엔티티
   */
  public OpenProfile toOpenProfile(User user) {
    return OpenProfile.builder()
        .nickname(nickname)
        .imageUrl(imageUrl)
        .description(description)
        .user(user)
        .build();
  }
}
