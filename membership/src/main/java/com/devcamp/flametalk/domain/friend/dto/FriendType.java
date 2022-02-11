package com.devcamp.flametalk.domain.friend.dto;

import com.devcamp.flametalk.domain.friend.domain.Friend;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 응답 시 친구 관계를 명확하게 보여주는 enum 입니다.
 */
@AllArgsConstructor
@Getter
public enum FriendType {

  DEFAULT(0),
  MARKED(1),
  HIDDEN(2),
  BLOCKED(3);

  private final int type;

  /**
   * 친구 엔티티 정보를 바탕으로 친구 관계 타입을 매칭합니다.
   *
   * @param friend 친구 엔티티
   * @return 친구 타입
   */
  public static FriendType of(Friend friend) {
    if (Boolean.TRUE.equals(friend.getIsBlocked())) {
      return BLOCKED;
    }
    if (Boolean.TRUE.equals(friend.getIsHidden())) {
      return HIDDEN;
    }
    if (Boolean.TRUE.equals(friend.getIsMarked())) {
      return MARKED;
    }
    return DEFAULT;
  }
}
