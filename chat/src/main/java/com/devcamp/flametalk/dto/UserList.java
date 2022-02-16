package com.devcamp.flametalk.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * RestTemplate 에 List 사용을 위한 객체입니다. 사용자의 id 리스트를 저장합니다.
 */
@ToString
@AllArgsConstructor
@Getter
public class UserList {

  private List<String> userIds;
  public UserList(){
    userIds = new ArrayList<>();
  }
}
