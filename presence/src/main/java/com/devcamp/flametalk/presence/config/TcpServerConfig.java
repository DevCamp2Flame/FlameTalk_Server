package com.devcamp.flametalk.presence.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.ip.tcp.TcpInboundGateway;
import org.springframework.integration.ip.tcp.connection.AbstractServerConnectionFactory;
import org.springframework.integration.ip.tcp.connection.TcpNioServerConnectionFactory;
import org.springframework.integration.ip.tcp.serializer.ByteArrayLfSerializer;
import org.springframework.messaging.MessageChannel;

@Configuration
@EnableIntegration
@EnableConfigurationProperties(TcpServerProperties.class)
public class TcpServerConfig {

  @Bean
  public AbstractServerConnectionFactory connectionFactory(
      TcpServerProperties tcpServerProperties) {
    TcpNioServerConnectionFactory connectionFactory = new TcpNioServerConnectionFactory(
        tcpServerProperties.getPort());
    connectionFactory.setSerializer(new ByteArrayLfSerializer());
    connectionFactory.setDeserializer(new ByteArrayLfSerializer());
    connectionFactory.setSingleUse(true);
    return connectionFactory;
  }

  @Bean
  public MessageChannel inboundChannel() {
    return new DirectChannel();
  }

  @Bean
  public MessageChannel replyChannel() {
    return new DirectChannel();
  }

  @Bean
  public TcpInboundGateway inboundGateway(AbstractServerConnectionFactory connectionFactory,
      MessageChannel inboundChannel, MessageChannel replyChannel) {
    TcpInboundGateway tcpInboundGateway = new TcpInboundGateway();
    tcpInboundGateway.setConnectionFactory(connectionFactory);
    tcpInboundGateway.setRequestChannel(inboundChannel);
    tcpInboundGateway.setReplyChannel(replyChannel);
    return tcpInboundGateway;
  }
}