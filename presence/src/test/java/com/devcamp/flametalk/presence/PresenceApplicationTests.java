package com.devcamp.flametalk.presence;

import static com.devcamp.flametalk.presence.response.StatusCode.SUCCESS_EXIT;

import com.devcamp.flametalk.presence.response.DefaultResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

@SpringBootTest
class PresenceApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void test() throws JsonProcessingException {
		System.out.println("To String {} " + DefaultResponse.toResponseEntity(HttpStatus.OK, SUCCESS_EXIT, null).toString());
		System.out.println("Object Mapper {}" + objectMapper.writeValueAsString(DefaultResponse.toResponseEntity(HttpStatus.OK, SUCCESS_EXIT, null)));
	}
}