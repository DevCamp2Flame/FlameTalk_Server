//package com.devcamp.flametalk.contoller;
//
//import com.devcamp.flametalk.domain.ChatRoomRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//@Controller
//@RequiredArgsConstructor
//@RequestMapping(value = "/")
//@Log4j2
//public class RoomController {
//
//  private final ChatRoomRepository repository;
//
//  @GetMapping(value = "/rooms")
//  public ModelAndView rooms() {
//    log.info("# All Chat Rooms");
//    ModelAndView mv = new ModelAndView("chat/rooms");
//
//    mv.addObject("list", repository.findAllRooms());
//
//    return mv;
//  }
//
//  // 채팅방 개설
//  @PostMapping(value = "/room")
//  public String create(@RequestParam String hostId, @RequestParam int count, @RequestParam short isOpen, RedirectAttributes attributes) {
//    log.info("# Create Chat Room , hostId: " + hostId);
//    attributes.addFlashAttribute("roomName", repository.createChatRoomDto(hostId, count, isOpen));
//    return "redirect:/chat/rooms";
//  }
//
//  // 채팅방 조회
//  @GetMapping("/room")
//  public void getRoom(String roomId, Model model) {
//    log.info("# get Chat Room, roomID : " + roomId);
//
//    model.addAttribute("room", repository.findRoomById(roomId));
//  }
//}