package com.devcamp.flametalk.presence.service;

import static com.devcamp.flametalk.presence.response.StatusCode.SUCCESS_ENTER;
import static com.devcamp.flametalk.presence.response.StatusCode.SUCCESS_EXIT;

import com.devcamp.flametalk.presence.dto.ChatroomRequest;
import com.devcamp.flametalk.presence.dto.ChatroomRequest.Type;
import com.devcamp.flametalk.presence.repository.RedisRepository;
import com.devcamp.flametalk.presence.response.DefaultResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class PresenceService {

  private final RedisRepository repository;
  private final ObjectMapper objectMapper;

  public String processMessage(String message) throws JsonProcessingException {
    log.info("Receive message : {}", message);

    // message 를 chatRoomRequest 로 변경
    ChatroomRequest request = objectMapper.readValue(message, ChatroomRequest.class);
    log.info("Request message : {}", message);

    // message 의 type 에 따라 Redis 에 기록 or 삭제
    if (Type.ENTER.equals(request.getType())) {
      repository.enterChatroom(request.getRoomId(), request.getUserId(), request.getDeviceId());
      return objectMapper.writeValueAsString(DefaultResponse.toResponseEntity(HttpStatus.CREATED, SUCCESS_ENTER, null));
    } else if ((Type.EXIT.equals(request.getType()))) {
      repository.exitChatroom(request.getRoomId(), request.getUserId(), request.getDeviceId());
      return objectMapper.writeValueAsString(DefaultResponse.toResponseEntity(HttpStatus.OK, SUCCESS_EXIT, null));
    }
    return "잘못된 타입입니다.";
  }
}