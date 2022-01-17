package com.devcamp.flametalk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * File 서버를 작동시키는 Application 클래스입니다.
 */
@EnableJpaAuditing
@SpringBootApplication
public class FileApplication {

  public static void main(String[] args) {
    SpringApplication.run(FileApplication.class, args);
  }

}
