package com.devcamp.flametalk.presence.config;

import static com.devcamp.flametalk.presence.response.StatusCode.MISMATCH_TYPE;
import static com.devcamp.flametalk.presence.response.StatusCode.SUCCESS_ENTER;
import static com.devcamp.flametalk.presence.response.StatusCode.SUCCESS_EXIT;

import com.devcamp.flametalk.presence.dto.ChatroomRequest;
import com.devcamp.flametalk.presence.dto.ChatroomRequest.Type;
import com.devcamp.flametalk.presence.repository.RedisRepository;
import com.devcamp.flametalk.presence.response.DefaultResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * WebSocket 메시지를 받고 처리합니다.
 * 메시지의 Type 이 ENTER 일 경우 채팅방 입장으로 판단하여 redis 에 채팅방 입장을 기록하고,
 * 메시지의 Type 이 EXIT 일 경우 채팅방 퇴장으로 판단하여 redis 에 채팅방 입장 기록을 삭제합니다.
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class WebSockChatHandler extends TextWebSocketHandler {

  private final RedisRepository repository;
  private final ObjectMapper objectMapper;

  @Override
  protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    String payload = message.getPayload();
    log.info("payload {}", payload);

    // String message 를 ChatroomRequest 객체로 변환
    ChatroomRequest request = objectMapper.readValue(payload, ChatroomRequest.class);
    log.info("request {}", request);

    // 응답 만들기
    String response;
    if (Type.ENTER.equals(request.getType())) {
      repository.enterChatroom(request.getRoomId(), request.getUserId(), request.getDeviceId());
      response = objectMapper.writeValueAsString(
          DefaultResponse.toResponseEntity(HttpStatus.CREATED, SUCCESS_ENTER, request));
    } else if ((Type.EXIT.equals(request.getType()))) {
      repository.exitChatroom(request.getRoomId(), request.getUserId(), request.getDeviceId());
      response = objectMapper.writeValueAsString(
          DefaultResponse.toResponseEntity(HttpStatus.OK, SUCCESS_EXIT, request));
    } else {
      response = objectMapper.writeValueAsString(
          DefaultResponse.toResponseEntity(HttpStatus.OK, MISMATCH_TYPE, request));
    }

    TextMessage textMessage = new TextMessage(response);
    session.sendMessage(textMessage);
  }
}