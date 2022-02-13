package com.devcamp.flametalk.chatroom.domain.chatroom.controller;

import com.devcamp.flametalk.chatroom.domain.chatroom.dto.ChatroomCreateRequest;
import com.devcamp.flametalk.chatroom.domain.chatroom.dto.ChatroomCreateResponse;
import com.devcamp.flametalk.chatroom.domain.chatroom.dto.ChatroomFilesResponse;
import com.devcamp.flametalk.chatroom.domain.chatroom.dto.ChatroomsResponse;
import com.devcamp.flametalk.chatroom.domain.chatroom.dto.JoinChatroomRequest;
import com.devcamp.flametalk.chatroom.domain.chatroom.dto.JoinChatroomResponse;
import com.devcamp.flametalk.chatroom.domain.chatroom.dto.ResponseType;
import com.devcamp.flametalk.chatroom.domain.chatroom.dto.UserChatroomCloseRequest;
import com.devcamp.flametalk.chatroom.domain.chatroom.dto.UserChatroomDetailResponse;
import com.devcamp.flametalk.chatroom.domain.chatroom.dto.UserChatroomSimpleResponse;
import com.devcamp.flametalk.chatroom.domain.chatroom.dto.UserChatroomUpdateRequest;
import com.devcamp.flametalk.chatroom.domain.chatroom.service.ChatroomService;
import com.devcamp.flametalk.chatroom.global.common.CommonResponse;
import com.devcamp.flametalk.chatroom.global.common.SingleDataResponse;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
   * 유저가 기존 채팅방에 입장합니다.
   *
   * @param request 입장 정보
   * @return 입장한 채팅방에 대한 정보
   */
  @PostMapping("/join")
  public ResponseEntity<SingleDataResponse<JoinChatroomResponse>> joinChatroom(
      @RequestBody @Valid JoinChatroomRequest request) {
    JoinChatroomResponse chatroom = chatroomService.joinChatroom(request);
    SingleDataResponse<JoinChatroomResponse> response = new SingleDataResponse<>();
    response.success(ResponseType.CHATROOM_JOIN_SUCCESS.getMessage(), chatroom);
    log.info("join chatroom {}", request.getChatroomId());
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
   * 유저가 참여중인 모든 채팅방에 대한 일부 정보를 조회합니다.
   *
   * @param userId 유저 id
   * @param isOpen 오픈 채팅방 여부
   * @return 채팅방 부분 정보에 대한 리스트
   */
  @GetMapping
  public ResponseEntity<SingleDataResponse<ChatroomsResponse>> findAllByUserId(
      @RequestHeader("USER-ID") String userId, @RequestParam boolean isOpen) {
    ChatroomsResponse chatrooms = chatroomService.findAllByUserId(userId, isOpen);
    SingleDataResponse<ChatroomsResponse> response = new SingleDataResponse<>();
    response.success(ResponseType.USER_CHATROOMS_SUCCESS.getMessage(), chatrooms);
    log.info("find all by user id" + chatrooms.toString());
    return ResponseEntity.ok().body(response);
  }

  /**
   * 채팅방에 업로드된 파일 정보 리스트를 조회합니다.
   *
   * @param chatroomId 채팅방 id
   * @return 업로드된 파일 정보에 대한 리스트
   */
  @GetMapping("/file/{chatroomId}")
  public ResponseEntity<SingleDataResponse<List<ChatroomFilesResponse>>> findAllFilesByChatroomId(
      @PathVariable String chatroomId) {
    List<ChatroomFilesResponse> chatroomFiles = chatroomService
        .findAllFilesByChatroomId(chatroomId);
    SingleDataResponse<List<ChatroomFilesResponse>> response = new SingleDataResponse<>();
    response.success(ResponseType.CHATROOM_FILES_SUCCESS.getMessage(), chatroomFiles);
    log.info("find all files uploaded in chatroom {}", chatroomId);
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

  /**
   * 유저가 확인한 채팅방의 마지막 메세지 정보를 갱신합니다.
   *
   * @param request 갱신될 유저 채팅방 정보
   * @return 갱신 결과에 따른 응답 정보
   */
  @PutMapping("/close")
  public ResponseEntity<CommonResponse> closeUserChatroom(
      @RequestBody @Valid UserChatroomCloseRequest request) {
    chatroomService.closeUserChatroom(request);
    CommonResponse response = new CommonResponse();
    response.success(ResponseType.USER_CHATROOM_CLOSE_SUCCESS.getMessage());
    log.info("close user chatroom" + request.getUserChatroomId());
    return ResponseEntity.ok().body(response);
  }

  /**
   * 요청받은 id에 해당하는 유저 채팅방을 삭제합니다.
   *
   * @param userChatroomId 유저 채팅방 id
   * @return 삭제 결과에 따른 응답 정보
   */
  @DeleteMapping("{userChatroomId}")
  public ResponseEntity<CommonResponse> closeUserChatroom(@PathVariable Long userChatroomId) {
    chatroomService.deleteUserChatroomById(userChatroomId);
    CommonResponse response = new CommonResponse();
    response.success(ResponseType.USER_CHATROOM_DELETE_SUCCESS.getMessage());
    log.info("delete user chatroom" + userChatroomId);
    return ResponseEntity.ok().body(response);
  }
}
