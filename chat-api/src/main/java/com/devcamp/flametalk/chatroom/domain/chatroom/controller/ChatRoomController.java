package com.devcamp.flametalk.chatroom.domain.chatroom.controller;

import static com.devcamp.flametalk.chatroom.global.common.Status.CREATED_CHATROOM;
import static org.springframework.http.HttpStatus.CREATED;

import com.devcamp.flametalk.chatroom.domain.chatroom.dto.ChatroomCreateRequest;
import com.devcamp.flametalk.chatroom.domain.chatroom.dto.ChatroomCreateResponse;
import com.devcamp.flametalk.chatroom.domain.chatroom.dto.ResponseType;
import com.devcamp.flametalk.chatroom.domain.chatroom.service.ChatroomService;
import com.devcamp.flametalk.chatroom.global.common.SingleDataResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/api/chatroom")
public class ChatRoomController {

  private final ChatroomService chatroomService;

  /**
   * 새로운 채팅방을 개설합니다.
   *
   * @param request
   * @return
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

  // todo : 다롬님이 하기
//  @GetMapping(value = "/rooms")
//  public ResponseEntity<DefaultResponse<ChatroomFindAllResponse>> findAllRoom() {
//
//    return mv;
//  }
}