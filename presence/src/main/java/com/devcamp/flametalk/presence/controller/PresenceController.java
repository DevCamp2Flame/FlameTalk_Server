package com.devcamp.flametalk.presence.controller;

import com.devcamp.flametalk.presence.repository.RedisRepository;
import com.devcamp.flametalk.presence.service.PresenceService;
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

  private final PresenceService presenceService;

  /**
   * 채팅방에 접속상태인 사용자 리스트를 반환합니다.
   *
   * @param roomId 채팅방 id
   * @return 채팅방에 접속상태인 사용자 리스트
   */
  @GetMapping("/{roomId}")
  public Set<String> getInsideChatroomUser(@PathVariable String roomId) {
    return presenceService.getInsideChatroomUser(roomId);
  }
}