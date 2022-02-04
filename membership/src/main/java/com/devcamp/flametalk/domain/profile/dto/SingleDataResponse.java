package com.devcamp.flametalk.domain.profile.dto;

import com.devcamp.flametalk.domain.profile.domain.ProfileResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 공통속성 + 단일 데이터입니다.
 *
 * @param <T> 적합한 response dto
 */
@Getter
@NoArgsConstructor
public class SingleDataResponse<T> extends CommonResponse {

  private T data;

  public SingleDataResponse(ProfileResponse response, T data) {
    super(response.getStatus(), response.getMessage());
    this.data = data;
  }
}
