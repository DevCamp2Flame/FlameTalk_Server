package com.devcamp.flametalk.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest({"spring.data.cassandra.port=9042",
    "spring.data.cassandra.keyspace-name=flametalk"})
@ExtendWith(SpringExtension.class)
class MessageRepositoryTest {

  @Autowired
  private MessageRepository messageRepository;

  @Test
  void save() {
    // given
    String messageId = "1";
    String messageType = "ENTER";
    String senderId = "2";
    String nickname = "darom";
    String roomId = "3";
    String contents = "hihi";
    LocalDateTime date = LocalDateTime.now();

    Message message = Message.builder()
        .message_id(messageId)
        .message_type(messageType)
        .sender_id(senderId)
        .nickname(nickname)
        .room_id(roomId)
        .contents(contents)
        .created_at(date)
        .build();

    // when
    Message save = messageRepository.save(message);

    // then
    assertEquals(save.getMessage_id(), messageId);
  }
}