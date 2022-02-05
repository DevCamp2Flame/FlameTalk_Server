package com.devcamp.flametalk.chatroom.domain.chatroom.service;

import com.devcamp.flametalk.chatroom.domain.chatroom.domain.Chatroom;
import com.devcamp.flametalk.chatroom.domain.chatroom.domain.ChatroomRepository;
import com.devcamp.flametalk.chatroom.domain.chatroom.domain.UserChatroom;
import com.devcamp.flametalk.chatroom.domain.chatroom.domain.UserChatroomRepository;
import com.devcamp.flametalk.chatroom.domain.chatroom.dto.ChatroomCreateRequest;
import com.devcamp.flametalk.chatroom.domain.chatroom.dto.ChatroomCreateResponse;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatroomService {

  private final ChatroomRepository chatroomRepository;
  private final UserChatroomRepository userChatroomRepository;

  public ChatroomCreateResponse create(ChatroomCreateRequest request) {

    // 채팅방 생성
    Chatroom chatroom = chatroomRepository.save(request.toChatroom());

    // 유저-채팅 row 생성
    // todo : findById 로 user 찾고 검증 후 user 에 추가

    // user 객체로 찾아서 검증
    // List<User> userList = new ArrayList<>();

    // 채팅방 첫 기본 생성시 이름을 위해 유저 getName -> String 으로
    String allTitle = String.join(",", request.getUsers());
    String title = allTitle.substring(0, Math.min(allTitle.length(), 50));

    List<UserChatroom> list = new ArrayList<>();
    request.getUsers().forEach(
        user -> {
          list.add(UserChatroom.builder()
              .chatroom(chatroom)
              .userId(user)
              .title(title)
              .inputLock(false)
              .build());
        }
    );

    userChatroomRepository.saveAll(list);

    return ChatroomCreateResponse.builder()
        .roomId(chatroom.getId())
        .hostId(chatroom.getHostId())
        .title(title)
        .isOpen(chatroom.isOpen())
        .build();
  }
}
