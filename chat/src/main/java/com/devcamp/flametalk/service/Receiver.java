package com.devcamp.flametalk.service;

import com.devcamp.flametalk.domain.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * 메시지가 발행되면 구독자에게 메시지가 전송됩니다.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class Receiver {

  // sub
  private final SimpMessagingTemplate template; // 특정 Broker로 메세지를 전달

  /**
   * topics 로 연결되어 메시지가 발행(publish)되면 대기하고 있던 receive 메소드가 실행됩니다.
   * /chat/room/{roomId} 를 구독 하고 있는 사람들에게 메시지 전송
   *
   * @param message message
   */
  @KafkaListener(id = "main-listener", topics = "flametalk")
  public void receive(Message message) {
    log.info("message='{}'", message);
    template.convertAndSend("/sub/chat/room/" + message.getRoom_id(), message);
  }
}