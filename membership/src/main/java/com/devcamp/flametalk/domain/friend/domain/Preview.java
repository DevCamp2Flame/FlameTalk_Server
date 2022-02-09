package com.devcamp.flametalk.domain.friend.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Friend 엔티티에 JSON 형식으로 저장되는 프로필 프리뷰 객체입니다.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Preview {

  private Long profileId;
  private String imageUrl;
  private String description;
}
