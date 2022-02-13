package com.devcamp.flametalk.presence.repository;

import java.util.Set;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Repository;

/**
 * 채팅방에 접속한 사용자를 기록하기 위한 저장소입니다.
 */
@RequiredArgsConstructor
@Repository
public class RedisRepository {

  private final RedisTemplate<String, String> redisTemplate; // roomId, { userId/deviceId, ... }
  private SetOperations<String, String> setOperations;
  private final String prefix = "[Presence]";

  @PostConstruct
  private void init() {
    setOperations = redisTemplate.opsForSet();
  }

  public void enterChatroom(String roomId, String userId, String deviceId) {
    setOperations.add(prefix + roomId, userId + "/" + deviceId);
  }

  public void exitChatroom(String roomId, String userId, String deviceId) {
    setOperations.remove(prefix + roomId, userId + "/" + deviceId);
  }

  public Set<String> getInsideChatroomUser(String roomId) {
    return setOperations.members(prefix + roomId);
  }
}