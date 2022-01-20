package com.devcamp.flametalk.dto;

import com.devcamp.flametalk.domain.ResponseMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 응답 결과에 포함되어야하는 공통 속성입니다.
 */
@Getter
public class CommonResponse {

  private int status;
  private String message;

  public void success(ResponseMessage message) {
    this.status = HttpStatus.OK.value();
    this.message = message.getMessage();
  }
}
