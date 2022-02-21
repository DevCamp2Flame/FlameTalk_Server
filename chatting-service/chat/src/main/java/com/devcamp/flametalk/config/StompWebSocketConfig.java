package com.devcamp.flametalk.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * Stomp 를 사용하기 위한 설정 파일입니다.
 */
@Configuration
@EnableWebSocketMessageBroker
public class StompWebSocketConfig implements WebSocketMessageBrokerConfigurer {

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry
        .addEndpoint("/stomp/chat") // websocket 핸드쉐이크 커넥션을 생성할 경로
        .setAllowedOriginPatterns("*")
        .withSockJS();
  }

  @Override
  public void configureMessageBroker(MessageBrokerRegistry registry) {
    registry
        /*
         publish - /app 경로로 시작하는 STOMP 메세지의 destination 헤더는 @MessageMapping 메서드로 라우팅.
         */
        .setApplicationDestinationPrefixes("/pub") // 메시지 발행 요청 "/app"
        /*
          subscribe - destination 헤더가 /topic 또는 /queue 로 시작한다면, 메시지 브로커로 바로(직접) 라우팅.
                      topic 은 1:N, queue 는 1:1
         */
        .enableSimpleBroker("/sub"); // 메시지 구독 요청 "/topic", "queue";
  }
}