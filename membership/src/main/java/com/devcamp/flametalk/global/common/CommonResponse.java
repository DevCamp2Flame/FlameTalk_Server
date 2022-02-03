package com.devcamp.flametalk.global.common;

import com.devcamp.flametalk.domain.profile.domain.ProfileResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 응답 결과에 포함되어야하는 공통 속성입니다.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse {

  private int status;
  private String message;

  public static CommonResponse from(ProfileResponse response) {
    return new CommonResponse(response.getStatus(), response.getMessage());
  }
}
