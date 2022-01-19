package com.devcamp.flametalk.user.domain;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

/**
 * 탈퇴한 사용자 정보를 캐싱하기 위한 저장소입니다.
 */
@RequiredArgsConstructor
@Repository
public class UserRedisRepository {

  private final RedisTemplate<String, String> redisTemplate; // userId, date
  private ValueOperations<String, String> valueOperations; // 레디스 서버에 데이터 작업을 수행하는 스프링 헬퍼 메서드의 집합

  @PostConstruct
  private void init() {
    valueOperations = redisTemplate.opsForValue();
  }

  public void saveIdleUser(String userId) {
    valueOperations.set(userId, LocalDateTime.now().toString(), 30, TimeUnit.DAYS);
  }

  public void updateIdleUser(String userId) {
    valueOperations.setIfPresent(userId, LocalDateTime.now().toString());
  }

  public void deleteIdleUser(String userId) {
    redisTemplate.delete(userId);
  }

  public String findLeaveDate(String userId) {
    return valueOperations.get(userId);
  }
}