package com.devcamp.flametalk.domain.friend.controller;

import com.devcamp.flametalk.domain.friend.dto.FriendCreateRequest;
import com.devcamp.flametalk.domain.friend.dto.FriendCreateResponse;
import com.devcamp.flametalk.domain.friend.service.FriendService;
import com.devcamp.flametalk.global.common.SingleDataResponse;
import com.devcamp.flametalk.global.common.Status;
import java.net.URI;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Membership 서버의 Friend API 처리 컨트롤러입니다.
 */
@Slf4j
@CrossOrigin("*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/membership/friend")
public class FriendController {

  private final FriendService friendService;

  /**
   * 친구 관계를 생성합니다.
   *
   * @param userId  친구 추가를 요청한 유저 id
   * @param request 등록할 친구 관계의 JSON 데이터
   * @return 생성된 친구 관계 정보
   */
  @PostMapping
  public ResponseEntity<SingleDataResponse<FriendCreateResponse>> create(
      @RequestHeader("USER-ID") String userId, @RequestBody @Valid FriendCreateRequest request) {
    FriendCreateResponse friend = friendService.save(userId, request);
    SingleDataResponse<FriendCreateResponse> response = new SingleDataResponse<>();
    if (friend == null) {
      response.fail(Status.FRIEND_NOT_FOUND, null);
      log.info("cannot create friend with not founded user");
      return ResponseEntity.ok().body(response);
    }
    response.success(Status.CREATED_FRIEND.getMessage(), friend);
    log.info("create friend relation {} ", friend.getFriendId());
    return ResponseEntity.created(URI.create("/api/membership/friend" + friend.getFriendId()))
        .body(response);
  }
}
