package com.devcamp.flametalk.chatroom.domain.chatroom.controller;

import static com.devcamp.flametalk.chatroom.global.common.StatusCode.CREATED_CHATROOM;
import static org.springframework.http.HttpStatus.CREATED;

import com.devcamp.flametalk.chatroom.domain.chatroom.dto.ChatroomCreateRequest;
import com.devcamp.flametalk.chatroom.domain.chatroom.dto.ChatroomCreateResponse;
import com.devcamp.flametalk.chatroom.global.common.DefaultResponse;
import com.devcamp.flametalk.chatroom.domain.chatroom.service.ChatroomService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/api/chatroom")
public class ChatRoomController {

  private final ChatroomService chatroomService;

  /**
   * 채팅방 개설
   * @param request
   * @return
   */
  @PostMapping
  public ResponseEntity<DefaultResponse<ChatroomCreateResponse>> create(
      @RequestBody @Valid ChatroomCreateRequest request) {
    chatroomService.create(request);
    return DefaultResponse.toResponseEntity(CREATED, CREATED_CHATROOM,
        chatroomService.create(request));
  }

  // todo : 다롬님이 하기
//  @GetMapping(value = "/rooms")
//  public ResponseEntity<DefaultResponse<ChatroomFindAllResponse>> findAllRoom() {
//
//    return mv;
//  }
}