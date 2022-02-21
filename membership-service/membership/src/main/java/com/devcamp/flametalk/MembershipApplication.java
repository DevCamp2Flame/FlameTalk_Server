package com.devcamp.flametalk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Profile API 서버를 작동시키는 Application 클래스입니다.
 */
@EnableDiscoveryClient
@EnableJpaAuditing
@SpringBootApplication
public class MembershipApplication {

  public static void main(String[] args) {
    SpringApplication.run(MembershipApplication.class, args);
  }

}
