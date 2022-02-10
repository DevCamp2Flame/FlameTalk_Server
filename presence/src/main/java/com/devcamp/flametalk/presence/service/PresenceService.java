package com.devcamp.flametalk.presence.service;

import com.devcamp.flametalk.presence.repository.RedisRepository;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class PresenceService {

  private final RedisRepository repository;

  public Set<String> getInsideChatroomUser(String roomId) {
    return repository.findInsideChatroomUser(roomId);
  }
}