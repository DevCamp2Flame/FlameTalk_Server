package com.devcamp.flametalk.chatapi.controller;

import static com.devcamp.flametalk.chatapi.response.StatusCode.CREATED_CHATROOM;
import static org.springframework.http.HttpStatus.CREATED;

import com.devcamp.flametalk.chatapi.dto.ChatroomCreateRequest;
import com.devcamp.flametalk.chatapi.dto.ChatroomCreateResponse;
import com.devcamp.flametalk.chatapi.response.DefaultResponse;
import com.devcamp.flametalk.chatapi.service.ChatroomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/api/chat")
public class RoomController {

  private final ChatroomService chatroomService;

  // todo : 다롬님이 하기
//  @GetMapping(value = "/rooms")
//  public ResponseEntity<DefaultResponse<ChatroomFindAllResponse>> findAllRoom() {
//
//    return mv;
//  }

  // 채팅방 개설
  @PostMapping(value = "/room")
  public ResponseEntity<DefaultResponse<ChatroomCreateResponse>> create(
      @RequestBody ChatroomCreateRequest chatroomCreateRequest) {

    System.out.println(chatroomCreateRequest.toString());

    return DefaultResponse.toResponseEntity(CREATED, CREATED_CHATROOM,
        chatroomService.create(chatroomCreateRequest));
  }
}