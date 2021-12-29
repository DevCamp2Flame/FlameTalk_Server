package com.devcamp.flametalk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class FlametalkApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlametalkApplication.class, args);
	}

}
