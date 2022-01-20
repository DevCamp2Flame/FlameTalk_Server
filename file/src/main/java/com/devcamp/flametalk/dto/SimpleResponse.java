package com.devcamp.flametalk.dto;

import com.devcamp.flametalk.domain.ResponseMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 단순 응답 결과 정보에 대한 response 클래스입니다.
 */
@Getter
public class SimpleResponse {

  private int status;
  private String message;

  public void success(ResponseMessage message) {
    this.status = HttpStatus.OK.value();
    this.message = message.getMessage();
  }
}
