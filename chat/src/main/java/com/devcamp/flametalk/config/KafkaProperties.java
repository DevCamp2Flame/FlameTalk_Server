package com.devcamp.flametalk.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * *.yml 파일에 있는 kafka property 를 자바 클래스에 바인딩하여 사용할 수 있게 합니다.
 */
@ConfigurationProperties("spring.kafka.consumer")
@Getter
@Setter
public class KafkaProperties {

  String bootstrapServers;
  String groupId;
}