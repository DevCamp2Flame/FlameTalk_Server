package com.devcamp.flametalk.dto;

import lombok.Getter;
import org.apache.http.HttpStatus;

@Getter
public class CommonResponse {
  private int status;

  private String message;

  public void setSuccessResponse(String message) {
    this.status = HttpStatus.SC_OK;
    this.message = message;
  }
}
