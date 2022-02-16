package com.devcamp.flametalk.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 응답 결과에 포함되어야하는 공통 속성입니다.
 */
@Getter
public class CommonResponse {

  private int status;
  private String message;

  public void success(String message) {
    this.status = HttpStatus.OK.value();
    this.message = message;
  }

  public void fail(int status, String message) {
    this.status = status;
    this.message = message;
  }
}
