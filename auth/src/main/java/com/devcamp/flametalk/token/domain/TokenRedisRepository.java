package com.devcamp.flametalk.token.domain;

import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

/**
 * Token 정보를 캐싱하기 위한 저장소입니다.
 */
@RequiredArgsConstructor
@Repository
public class TokenRedisRepository {

  private final RedisTemplate<String, String> redisTemplate; // userId, date
  private ValueOperations<String, String> valueOperations; // 레디스 서버에 데이터 작업을 수행하는 스프링 헬퍼 메서드의 집합

  @PostConstruct
  private void init() {
    valueOperations = redisTemplate.opsForValue();
  }

  public void saveAccessToken(String key, String accessToken, long timeout) {
    valueOperations.set("accessToken:" + key, accessToken, timeout, TimeUnit.MILLISECONDS);
  }

  public void saveRefreshToken(String key, String refreshToken, long timeout) {
    valueOperations.set("refreshToken:" + key, refreshToken, timeout, TimeUnit.MILLISECONDS);
  }

  public void deleteToken(String key) {
    redisTemplate.delete("accessToken:" + key);
    redisTemplate.delete("refreshToken:" + key);
  }

  public String findToken(String userId, String deviceId, String token) {
    return valueOperations.get(userId + deviceId + token);
  }
}