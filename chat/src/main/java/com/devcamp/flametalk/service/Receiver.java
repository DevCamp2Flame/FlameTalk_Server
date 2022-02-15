package com.devcamp.flametalk.service;

import com.devcamp.flametalk.domain.Message;
import com.devcamp.flametalk.dto.UserList;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * 메시지가 발행되면 구독자에게 메시지가 전송됩니다.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class Receiver {

  // sub
  private final SimpMessagingTemplate template; // 특정 Broker로 메세지를 전달
  private final RestTemplate restTemplate;

  /**
   * topics 로 연결되어 메시지가 발행(publish)되면 대기하고 있던 receive 메소드가 실행됩니다. /chat/room/{roomId} 를 구독 하고 있는
   * 사람들에게 메시지 전송하고, 채팅방에 현재 접속 상태가 아니라면 fcm 알림을 보냅니다.
   *
   * @param message message
   */
  @KafkaListener(id = "main-listener", topics = "flametalk")
  public void receive(Message message) {
    log.info("message='{}'", message);

    // 채팅방 전체 사용자, 채팅방에 접속 상태인 사용자
    List<String> chatroomUser = getChatroomUsers(message.getRoom_id());
    List<String> presenceUser = getInsideUsers(message.getRoom_id());

    // '전체 사용자 - 접속 상태인 사용자' 에 해당하는 사람들에게 FCM 메시지 전송
    chatroomUser.removeAll(presenceUser);
    restTemplate.postForEntity("localhost:8087/send", chatroomUser, UserList.class);

    // /chat/room/{roomId} 를 구독 하고 있는 사람들에게 메시지 전송
    template.convertAndSend("/sub/chat/room/" + message.getRoom_id(), message);
  }

  private List<String> getChatroomUsers(String roomId) {
    ResponseEntity<UserList> response = restTemplate.getForEntity(
        "localhost:8084/api/chatroom/" + roomId + "/users", UserList.class);
    return Objects.requireNonNull(response.getBody()).getUserIds();
  }

  private List<String> getInsideUsers(String roomId) {
    ResponseEntity<UserList> response = restTemplate.getForEntity(
        "localhost:8086/api/presence/" + roomId, UserList.class);
    return Objects.requireNonNull(response.getBody()).getUserIds();
  }
}