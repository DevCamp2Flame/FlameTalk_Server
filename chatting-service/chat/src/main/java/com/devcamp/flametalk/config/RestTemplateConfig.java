package com.devcamp.flametalk.config;

import java.util.concurrent.TimeUnit;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * RestTemplate 을 Bean 으로 등록하기 위한 설정 파일입니다.
 */
@Configuration
public class RestTemplateConfig {

  /**
   * RestTemplate 의 기본 설정을 합니다.
   *
   * @return RestTemplate
   */
  @Bean
  public RestTemplate restTemplate() {

    HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();

    HttpClient client = HttpClientBuilder.create()
        .setMaxConnTotal(50) // 커넥션 풀 적용 - 최대 커넥션 수
        .setMaxConnPerRoute(20) // IP 도메인당 최대 커넥션 수
        .evictIdleConnections(2000L, TimeUnit.MILLISECONDS) // keepalive 시간동안 미 사용한 idle 커넥션을 주기적으로 지움
        .build();

    factory.setHttpClient(client);
    factory.setConnectTimeout(2000); // 서버에 소켓 연결을 맺을 때 연결 시간 초과, ms
    factory.setReadTimeout(5000); //  읽기 시간 초과, ms

    return new RestTemplate(factory);
  }
}