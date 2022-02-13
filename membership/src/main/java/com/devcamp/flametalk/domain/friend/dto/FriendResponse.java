package com.devcamp.flametalk.domain.friend.dto;

import com.devcamp.flametalk.domain.friend.domain.Preview;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 유저의 친구 정보 응답을 위한 클래스입니다.
 */
@AllArgsConstructor
@Getter
public class FriendResponse implements Comparable<FriendResponse> {

  private Long friendId;
  private String userId;
  private String nickname;
  private Preview preview;

  @Override
  public int compareTo(FriendResponse o) {
    return this.nickname.compareTo(o.nickname);
  }
}
