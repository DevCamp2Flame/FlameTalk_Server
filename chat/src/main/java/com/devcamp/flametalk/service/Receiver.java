package com.devcamp.flametalk.service;

import com.devcamp.flametalk.domain.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class Receiver {

  // sub
  private final SimpMessagingTemplate template; // 특정 Broker로 메세지를 전달

  // topics 로 연결되어있음. 메시지가 발행(publish)되면 대기하고 있던 receive 메소드가 실행됨.
  // "/chat/room/{roomId}" 를 구독 하고 있는 사람들에게 메시지 전송
  @KafkaListener(id = "main-listener", topics = "flametalk")
  public void receive(Message message) {
    log.info("message='{}'", message);
    template.convertAndSend("/sub/chat/room/" + message.getRoom_id(), message);
  }
}