package com.devcamp.flametalk.presence.config;

import com.devcamp.flametalk.presence.service.PresenceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;

@RequiredArgsConstructor
@MessageEndpoint
public class TcpServerEndpoint {

  private final PresenceService presenceService;

  @ServiceActivator(inputChannel = "inboundChannel", async = "true")
  public String process(String message) throws JsonProcessingException {
    return presenceService.processMessage(message);
  }
}