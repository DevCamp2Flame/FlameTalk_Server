package com.devcamp.flametalk.chatroom.domain.chatroom.dto;

import com.devcamp.flametalk.chatroom.domain.chatroom.domain.UserChatroom;
import com.devcamp.flametalk.chatroom.domain.profile.domain.Profile;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 유저 채팅방 상세 정보 응답을 위한 클래스입니다.
 */
@AllArgsConstructor
@Getter
public class UserChatroomDetailResponse {

  private Boolean isOpen;
  private Long profileId;
  private String profileNickname;
  private String profileImage;
  private List<ProfileSimpleResponse> profiles;
  private List<String> files;

  /**
   * UserChatroomDetailResponse 객체로 생성하는 정적 팩토리 메소드입니다.
   *
   * @param userChatroom   응답할 유저 채팅방 데이터
   * @param userProfile    채팅방에서 사용하는 유저 프로필
   * @param friendProfiles 유저 외 채팅방에 참여중인 타 유저들의 프로필 리스트
   * @param files          채팅방에서 업로드 된 최대 4개의 파일 S3 URL 리스트
   * @return UserChatroomDetailResponse 객체
   */
  public static UserChatroomDetailResponse of(UserChatroom userChatroom, Profile userProfile,
      List<ProfileSimpleResponse> friendProfiles, List<String> files) {
    return new UserChatroomDetailResponse(
        userChatroom.getChatroom().getIsOpen(),
        userProfile.getId(),
        userChatroom.getUser().getNickname(),
        userProfile.getImageUrl(),
        friendProfiles,
        files
    );
  }
}
