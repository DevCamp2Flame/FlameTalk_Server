package com.devcamp.flametalk.chatroom.global.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * 응답 결과에 포함되어야하는 공통 속성입니다.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse {

  private int status;
  private String message;

  public void success(String message) {
    this.status = HttpStatus.OK.value();
    this.message = message;
  }
}
