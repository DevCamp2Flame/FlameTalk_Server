package com.devcamp.flametalk.service;

import com.devcamp.flametalk.domain.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * 메시지 발행 역할을 합니다.
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class Sender {

  // pub
  private final KafkaTemplate<String, Message> kafkaTemplate;

  public void send(String topic, Message data) {
    log.info("sending data='{}' to topic='{}'", data, topic);
    kafkaTemplate.send(topic, data); // send to clients via websocket(STOMP)
  }
}