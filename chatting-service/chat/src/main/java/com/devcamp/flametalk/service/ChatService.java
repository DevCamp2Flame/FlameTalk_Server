package com.devcamp.flametalk.service;

import static com.devcamp.flametalk.response.StatusCode.SUCCESS_READ_MESSAGE;

import com.devcamp.flametalk.domain.Message;
import com.devcamp.flametalk.domain.MessageRepository;
import com.devcamp.flametalk.response.DefaultResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * 채팅 메시지 관련 비즈니스 로직을 처리하는 클래스입니다.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {

  private final MessageRepository messageRepository;

  /**
   * 채팅방 메시지 히스토리를 반환합니다.
   *
   * @param roomId            채팅방 id
   * @param lastReadMessageId 마지막으로 읽은 메시지 id
   * @return 메시지 히스토리
   */
  public ResponseEntity<DefaultResponse<List<Message>>> getChattingHistory(String roomId,
      String lastReadMessageId) {

    List<Message> messages;
    if (lastReadMessageId == null) {
      messages = messageRepository.findByRoomId(roomId);
    } else {
      messages = messageRepository.findByRoomIdAndMessageId(roomId, lastReadMessageId);
    }

    return DefaultResponse.toResponseEntity(HttpStatus.OK, SUCCESS_READ_MESSAGE, messages);
  }
}