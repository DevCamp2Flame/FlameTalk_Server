package com.devcamp.flametalk.domain;

import com.devcamp.flametalk.dto.ChatRoomDto;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

/**
 * 채팅방 레파지토리.
 */
@Repository
public class ChatRoomRepository {

  private Map<String, ChatRoomDto> chatRoomDtoMap;

  @PostConstruct
  private void init() {
    chatRoomDtoMap = new LinkedHashMap<>();
  }

  /**
   * 채팅방 생성.
   *
   * @return 채팅방 생성 순서 최근 순으로 반환
   */
  public ChatRoomDto createChatRoomDto(String name) {
    ChatRoomDto room = ChatRoomDto.create(name);
    chatRoomDtoMap.put(room.getRoomId(), room);

    return room;
  }

  public ChatRoomDto findRoomById(String id) {
    return chatRoomDtoMap.get(id);
  }

  /**
   * 전체 채팅방 조회.
   *
   * @return 채팅방 생성 순서 최근 순으로 반환
   */
  public List<ChatRoomDto> findAllRooms() {
    //채팅방 생성 순서 최근 순으로 반환
    List<ChatRoomDto> result = new ArrayList<>(chatRoomDtoMap.values());
    Collections.reverse(result);

    return result;
  }
}