package com.devcamp.flametalk.presence.service;

import com.devcamp.flametalk.presence.repository.RedisRepository;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
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

  /**
   * 채팅방에 접속 상태인 사용자 id 리스트를 반환합니다.
   *
   * @param roomId 채팅방 id
   * @return 채팅방에 접속 상태인 사용자 id 리스트
   */
  public List<String> getInsideChatroomUser(String roomId) {
    Set<String> userIdAndDeviceIdList = repository.getInsideChatroomUser(roomId);
    if (userIdAndDeviceIdList.isEmpty()) {
      return Collections.emptyList();
    }

    return userIdAndDeviceIdList.stream()
        .map(userIdAndDeviceId -> userIdAndDeviceId.split("/", 2)[0])
        .collect(Collectors.toList());
  }
}