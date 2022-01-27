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
import org.springframework.ui.Model;
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

//  @GetMapping(value = "/rooms")
//  public ModelAndView rooms() {
//    log.info("# All Chat Rooms");
//    ModelAndView mv = new ModelAndView("chat/rooms");
//
//    List<> repository.findAllRooms());
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

  // 채팅방 조회
  @GetMapping("/room")
  public void getRoom(String roomId, Model model) {
    log.info("# get Chat Room, roomID : " + roomId);

//    model.addAttribute("room", repository.findRoomById(roomId));
  }
}