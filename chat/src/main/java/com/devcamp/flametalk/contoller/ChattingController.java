package com.devcamp.flametalk.contoller;

import com.devcamp.flametalk.model.ChattingMessage;
import com.devcamp.flametalk.service.Receiver;
import com.devcamp.flametalk.service.Sender;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class ChattingController {

  @Autowired
  private Sender sender;

  @Autowired
  private Receiver receiver;

  @Autowired
  private ChattingHistoryDao chattingHistoryDao;

  private static String BOOT_TOPIC = "kafka-chatting";

  // "url/app/message"로 들어오는 메시지를 "/topic/public"을 구독하고있는 사람들에게 송신
  @MessageMapping("/message")
  // @MessageMapping works for WebSocket protocol communication. This defines the URL mapping.
  // @SendTo("/topic/public")//websocket subscribe topic& direct send
  public void sendMessage(ChattingMessage message) throws Exception {
    message.setTimeStamp(System.currentTimeMillis());
    chattingHistoryDao.save(message);
    sender.send(BOOT_TOPIC, message);

  }

  @RequestMapping("/history")
  public List<ChattingMessage> getChattingHistory() throws Exception {
    System.out.println("history!");
    return chattingHistoryDao.get();
  }

  @MessageMapping("/file")
  @SendTo("/topic/chatting")
  public ChattingMessage sendFile(ChattingMessage message) throws Exception {
    return new ChattingMessage(message.getFileName(), message.getRawData(), message.getUser());
  }
}
