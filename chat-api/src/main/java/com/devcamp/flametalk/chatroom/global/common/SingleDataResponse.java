package com.devcamp.flametalk.chatroom.global.common;

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

  public void success(String message, T data) {
    this.success(message);
    this.data = data;
  }
}
