package com.devcamp.flametalk.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest({"spring.data.cassandra.port=9042",
    "spring.data.cassandra.keyspace-name=test"})
@ExtendWith(SpringExtension.class)
class MessageRepositoryTest {

  @Autowired
  private MessageRepository messageRepository;

  @Test
  void save() {
    // given
    String messageId = "2";
    String contents = "hihi";
    String roomId = "1";
    String senderId = "2";
    LocalDateTime date = LocalDateTime.now();

    Message message = Message.builder()
        .message_id(messageId)
        .contents(contents)
        .room_id(roomId)
        .sender_id(senderId)
        .created_at(date)
        .build();

    // when
    Message save = messageRepository.save(message);

    // then
    assertEquals(save.getMessage_id(), messageId);
  }
}