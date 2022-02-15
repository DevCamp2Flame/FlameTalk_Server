package com.devcamp.flametalk.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * RestTemplate 에 List 사용을 위한 객체입니다. 사용자의 id 리스트를 저장합니다.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserList {

  private List<String> userIds;
}
