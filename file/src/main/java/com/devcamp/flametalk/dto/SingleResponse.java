package com.devcamp.flametalk.dto;

import lombok.Getter;

/**
 * 공통속성 + 단일 데이터입니다.
 *
 * @param <T> 적합한 response dto
 */
@Getter
public class SingleResponse<T> extends CommonResponse {

  private T data;

  public SingleResponse(T data) {
    super();
    this.data = data;
  }
}
