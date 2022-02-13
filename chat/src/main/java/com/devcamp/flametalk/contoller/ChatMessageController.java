package com.devcamp.flametalk.contoller;

import com.devcamp.flametalk.domain.Message;
import com.devcamp.flametalk.response.DefaultResponse;
import com.devcamp.flametalk.service.ChatService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 채팅 메시지와 관련된 컨트롤러입니다.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/chat")
public class ChatMessageController {

  private final ChatService chatService;

  @GetMapping("/history/{roomId}")
  public ResponseEntity<DefaultResponse<List<Message>>> getChattingHistory(
      @PathVariable String roomId, @RequestParam(required = false) String lastReadMessageId) {
    System.out.println("lastReadMessageId = " + lastReadMessageId);
    return chatService.getChattingHistory(roomId, lastReadMessageId);
  }
}