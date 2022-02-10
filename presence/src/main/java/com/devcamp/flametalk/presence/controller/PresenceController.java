package com.devcamp.flametalk.presence.controller;

import com.devcamp.flametalk.presence.repository.RedisRepository;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 채팅방에 접속한 사용자와 관련된 컨트롤러입니다.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/presence")
public class PresenceController {

  private final RedisRepository repository;

  @GetMapping("/{roomId}")
  public Set<String> findInsideChatroomUser(@PathVariable String roomId) {
    return repository.findInsideChatroomUser(roomId);
  }
}