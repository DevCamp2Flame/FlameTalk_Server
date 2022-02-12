package com.devcamp.flametalk.domain.friend.dto;

import com.devcamp.flametalk.domain.friend.domain.Friend;
import com.devcamp.flametalk.domain.friend.domain.Preview;
import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * 친구 관계 수정 정보 응답을 위한 클래스입니다.
 */
@AllArgsConstructor
@Getter
public class FriendUpdateResponse {

  public Long friendId;
  public String userId;
  public String nickname;
  public Long assignedProfileId;
  public String type;
  public Preview preview;

  /**
   * Friend 엔티티와 Preview 객체를 통해 응답 객체를 생성하는 정적 팩토리 메소드입니다.
   *
   * @param friend               Friend 엔티티
   * @param friendProfilePreview 친구 프로필에 대한 Preview 객체
   * @return 수정된 친구관계 정보 응답 객체
   */
  public static FriendUpdateResponse of(Friend friend, Preview friendProfilePreview) {
    return new FriendUpdateResponse(
        friend.getId(),
        friend.getUserFriend().getId(),
        friend.getUserFriend().getNickname(),
        friend.getProfile().getId(),
        FriendType.of(friend).name(),
        friendProfilePreview
    );
  }
}
