package com.devcamp.flametalk.dto;

import lombok.Getter;

@Getter
public class SingleResponse<T> extends CommonResponse{
  private T data;

  public SingleResponse(T data) {
    super();
    this.data = data;
  }
}
