package com.devcamp.flametalk.dto;

import lombok.Getter;
import org.apache.http.HttpStatus;

/**
 * 응답 결과에 포함되어야하는 공통 속성입니다.
 */
@Getter
public class CommonResponse {

  private int status;

  private String message;

  public void setSuccessResponse(String message) {
    this.status = HttpStatus.SC_OK;
    this.message = message;
  }
}
