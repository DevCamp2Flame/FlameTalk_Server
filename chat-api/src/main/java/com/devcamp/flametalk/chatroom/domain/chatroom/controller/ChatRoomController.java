package com.devcamp.flametalk.chatroom.domain.chatroom.controller;

import com.devcamp.flametalk.chatroom.domain.chatroom.dto.ChatroomCreateRequest;
import com.devcamp.flametalk.chatroom.domain.chatroom.dto.ChatroomCreateResponse;
import com.devcamp.flametalk.chatroom.domain.chatroom.dto.ResponseType;
import com.devcamp.flametalk.chatroom.domain.chatroom.dto.UserChatroomDetailResponse;
import com.devcamp.flametalk.chatroom.domain.chatroom.dto.UserChatroomSimpleResponse;
import com.devcamp.flametalk.chatroom.domain.chatroom.dto.UserChatroomUpdateRequest;
import com.devcamp.flametalk.chatroom.domain.chatroom.service.ChatroomService;
import com.devcamp.flametalk.chatroom.global.common.SingleDataResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 채팅 API 처리 컨트롤러입니다. 채팅방과 관련된 HTTP 요청을 처리합니다.
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/api/chatroom")
public class ChatRoomController {

  private final ChatroomService chatroomService;

  /**
   * 유저가 새로운 채팅방을 생성합니다.
   *
   * @param userId  로그인한 유저 id
   * @param request 생성하고자하는 채팅방 데이터
   * @return 생성된 채팅방에 대한 정보
   */
  @PostMapping
  public ResponseEntity<SingleDataResponse<ChatroomCreateResponse>> create(
      @RequestHeader("USER-ID") String userId, @RequestBody @Valid ChatroomCreateRequest request) {
    ChatroomCreateResponse chatroom = chatroomService.save(userId, request);
    SingleDataResponse<ChatroomCreateResponse> response = new SingleDataResponse<>();
    response.success(ResponseType.CHATROOM_CREATE_SUCCESS.getMessage(), chatroom);
    log.info(chatroom.getChatroomId());
    return ResponseEntity.ok().body(response);
  }

  /**
   * 유저가 참여중인 채팅방에 대한 상세정보를 조회합니다.
   *
   * @param userChatroomId 유저가 참여중인 채팅방 id
   * @return 채팅방 상세 정보
   */
  @GetMapping("/{userChatroomId}")
  public ResponseEntity<SingleDataResponse<UserChatroomDetailResponse>> findByUserChatroomId(
      @PathVariable Long userChatroomId) {
    UserChatroomDetailResponse userChatroomDetail = chatroomService.findByUserChatroomId(
        userChatroomId);
    SingleDataResponse<UserChatroomDetailResponse> response = new SingleDataResponse<>();
    response.success(ResponseType.CHATROOM_DETAIL_SUCCESS.getMessage(), userChatroomDetail);
    log.info("find user chatroom by id " + userChatroomDetail.toString());
    return ResponseEntity.ok().body(response);
  }

  /**
   * 유저 채팅방 정보를 수정합니다.
   *
   * @param userChatroomId 유저 채팅방 id
   * @param request        수정할 유저 채팅방 JSON 데이터
   * @return 수정된 유저 채팅방 단순 정보
   */
  @PutMapping("/{userChatroomId}")
  public ResponseEntity<SingleDataResponse<UserChatroomSimpleResponse>> updateUserChatroom(
      @PathVariable Long userChatroomId, @RequestBody @Valid UserChatroomUpdateRequest request) {
    UserChatroomSimpleResponse userChatroom = chatroomService.updateUserChatroom(userChatroomId,
        request);
    SingleDataResponse<UserChatroomSimpleResponse> response = new SingleDataResponse<>();
    response.success(ResponseType.CHATROOM_UPDATE_SUCCESS.getMessage(), userChatroom);
    log.info("find user chatroom by id " + userChatroom.toString());
    return ResponseEntity.ok().body(response);
  }
}
