package com.devcamp.flametalk.presence.service;

import com.devcamp.flametalk.presence.repository.RedisRepository;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 채팅방에 접속한 사용자와 관련된 서비스입니다.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class PresenceService {

  private final RedisRepository repository;

  public Set<String> getInsideChatroomUser(String roomId) {
    return repository.getInsideChatroomUser(roomId);
  }
}