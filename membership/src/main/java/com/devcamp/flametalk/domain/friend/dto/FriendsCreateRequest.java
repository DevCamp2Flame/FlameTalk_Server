package com.devcamp.flametalk.domain.friend.dto;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 연락처 친구들 추가 요청을 위한 클래스입니다.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class FriendsCreateRequest {

  @NotNull
  private List<String> phoneNumbers = new ArrayList<>();
}
