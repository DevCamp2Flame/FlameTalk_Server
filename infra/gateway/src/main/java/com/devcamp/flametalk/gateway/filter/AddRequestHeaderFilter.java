package com.devcamp.flametalk.gateway.filter;

import com.devcamp.flametalk.gateway.dto.UserDto;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * ACCESS-TOKEN 정보를 auth 서버에 전달하여 올바른 사용자인지 확인하고, 내부 서버 요청 헤더로 USER-ID, DEVICE-ID 를 전달합니다.
 */
@Component
public class AddRequestHeaderFilter extends
    AbstractGatewayFilterFactory<AddRequestHeaderFilter.Config> {

  /**
   * 필터를 초기화할 때 Config 클래스를 활용하여 필터를 커스텀할 수 있다.
   */
  public static class Config {

  }

  public AddRequestHeaderFilter() {
    super(Config.class);
  }

  @Override
  public GatewayFilter apply(Config config) {
    return (exchange, chain) -> {
      // accessToken 추출
      ServerHttpRequest request = exchange.getRequest();
      String token = request.getHeaders().getFirst("ACCESS-TOKEN");

      // request 생성
      HttpHeaders headers = new HttpHeaders();
      headers.setAccept(Arrays.asList(new MediaType[]{MediaType.APPLICATION_JSON}));
      headers.setContentType(MediaType.APPLICATION_JSON);
      headers.set("ACCESS-TOKEN", token);
      HttpEntity<String> entity = new HttpEntity<String>("", headers);

      // auth 서버에서 인증
      RestTemplate restTemplate = new RestTemplate();
      ResponseEntity<UserDto> responseEntity = restTemplate.exchange(
          "http://localhost:8081/api/auth/user", HttpMethod.GET, entity, UserDto.class);
      System.out.println(responseEntity.getBody());

      // responseEntity null 이면 에러처리 - 올바른 토큰이 아님
      if (responseEntity.getBody() == null) {
        return customError(exchange);
      }

      return chain.filter(newExchange(exchange, responseEntity));
    };
  }

  private ServerWebExchange newExchange(ServerWebExchange exchange,
      ResponseEntity<UserDto> responseEntity) {
    // 기존 header 제거 후 추가 header 생성
    return exchange.mutate().request(exchange.getRequest().mutate()
        .header("USER-ID", responseEntity.getBody().getUserId())
        .header("DEVICE-ID", responseEntity.getBody().getDeviceId())
        .headers(httpHeaders -> httpHeaders.remove("ACCESS-TOKEN"))
        .build()
    ).build();
  }

  private Mono<Void> customError(ServerWebExchange exchange) {
    ServerHttpResponse response = exchange.getResponse();
    response.setStatusCode(HttpStatus.OK);
    String message = "{\n"
        + "    \"status\": 302,\n"
        + "    \"messsage\": \"Invalid Token\"\n"
        + "}";
    byte[] bytes = message.getBytes(StandardCharsets.UTF_8);
    response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
    return response.writeWith(Mono.just(response.bufferFactory().wrap(bytes)));
  }
}