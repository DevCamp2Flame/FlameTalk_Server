package com.devcamp.flametalk.contoller;

import com.devcamp.flametalk.domain.Message;
import com.devcamp.flametalk.domain.MessageRepository;
import com.devcamp.flametalk.dto.MessageRequest;
import com.devcamp.flametalk.service.Sender;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class StompChatController {

  private final Sender sender;
  private final MessageRepository messageRepository;

  private static final String TOPIC = "flametalk";

  // client가 send 할 수 있는 경로
  // stompConfig 에서 설정한 applicationDestinationPrefixes 와 @MessageMapping 경로가 병합됨
  // "/pub/chat/message" 로 들어오는 메시지를 /chat/room/{roomId} 를 구독하고 있는 사람들에게 송신
  @MessageMapping(value = "/chat/message")
  public void message(MessageRequest messageRequest) {
    if(MessageRequest.MessageType.INVITE.equals(messageRequest.getType())) {
      messageRequest.setContents(messageRequest.getNickname() + "님에 의해 초대되었습니다.");
    }
    else if (MessageRequest.MessageType.ENTER.equals(messageRequest.getType())) {
      messageRequest.setContents(messageRequest.getNickname() + "님이 채팅방에 참여하였습니다.");
    }

    // 메시지 저장
    Message message = messageRepository.save(messageRequest.toEntity());
    System.out.println("message: " + message.toString());

    // 메시지 송신
    sender.send(TOPIC, message);
  }

  // todo : 파일 송신
//  @MessageMapping("/chat/file")
//  @SendTo("/sub/chat/room/{roomId}")
//  public Message sendFile(MessageRequest message) {
//    return new Message(message.getFileName(), message.getRawData(), message.getUser());
//  }
}