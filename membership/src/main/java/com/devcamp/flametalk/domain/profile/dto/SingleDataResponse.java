package com.devcamp.flametalk.domain.profile.dto;

import com.devcamp.flametalk.domain.profile.domain.ProfileResponse;
import lombok.Getter;

/**
 * 공통속성 + 단일 데이터입니다.
 *
 * @param <T> 적합한 response dto
 */
@Getter
public class SingleDataResponse<T> extends CommonResponse {

  private T data;

  public SingleDataResponse(ProfileResponse response, T data) {
    super(200, response.getMessage());
    this.data = data;
  }
}
