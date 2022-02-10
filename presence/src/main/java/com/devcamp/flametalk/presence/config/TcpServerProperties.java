package com.devcamp.flametalk.presence.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("tcp.server")
@Getter
@Setter
public class TcpServerProperties {

  int port;
}