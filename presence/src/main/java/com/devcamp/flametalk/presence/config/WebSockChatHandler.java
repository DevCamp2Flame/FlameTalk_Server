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

        ChatroomRequest request = objectMapper.readValue(payload, ChatroomRequest.class);
        log.info("request {}", request);

        // 응답 만들기
        String response;
        if(Type.ENTER.equals(request.getType())) {
            repository.enterChatroom(request.getRoomId(), request.getUserId(), request.getDeviceId());
            response = objectMapper.writeValueAsString(
                DefaultResponse.toResponseEntity(HttpStatus.CREATED, SUCCESS_ENTER, request));
        }
        else if ((Type.EXIT.equals(request.getType()))) {
            repository.exitChatroom(request.getRoomId(), request.getUserId(), request.getDeviceId());
            response = objectMapper.writeValueAsString(DefaultResponse.toResponseEntity(HttpStatus.OK, SUCCESS_EXIT, request));
        }
        else response = objectMapper.writeValueAsString(DefaultResponse.toResponseEntity(HttpStatus.OK, MISMATCH_TYPE, request));

        TextMessage textMessage = new TextMessage(response);
        session.sendMessage(textMessage);
    }
}