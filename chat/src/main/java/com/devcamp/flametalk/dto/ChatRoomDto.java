//package com.devcamp.flametalk.dto;
//
//import java.util.HashSet;
//import java.util.Set;
//import java.util.UUID;
//import lombok.Getter;
//import lombok.Setter;
//import org.springframework.web.socket.WebSocketSession;
//
//@Getter
//@Setter
//public class ChatRoomDto {
//
//  private String roomId;
//  private String hostId;
//  private int count;
//  private short isOpen;
//
////  @Transient
//  // WebSocketSession은 Spring에서 Websocket Connection이 맺어진 세션
//  private Set<WebSocketSession> sessions = new HashSet<>();
//
//  public static ChatRoomDto create(String hostId, int count, short isOpen) {
//    ChatRoomDto room = new ChatRoomDto();
//
//    room.roomId = UUID.randomUUID().toString();
//    room.hostId = hostId;
//    room.count = count;
//    room.isOpen = isOpen;
//    return room;
//  }
//}