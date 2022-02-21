package com.devcamp.flametalk.domain.profile.controller;

import com.devcamp.flametalk.domain.friend.service.FriendService;
import com.devcamp.flametalk.domain.profile.domain.ProfileResponse;
import com.devcamp.flametalk.domain.profile.dto.ProfileDetailResponse;
import com.devcamp.flametalk.domain.profile.dto.ProfileRequest;
import com.devcamp.flametalk.domain.profile.dto.ProfilesResponse;
import com.devcamp.flametalk.domain.profile.service.ProfileService;
import com.devcamp.flametalk.global.common.CommonResponse;
import com.devcamp.flametalk.global.common.SingleDataResponse;
import java.net.URI;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Membership 서버의 Profile API 처리 컨트롤러입니다.
 */
@Slf4j
@CrossOrigin("*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/membership/profile")
public class ProfileController {

  private final ProfileService profileService;
  private final FriendService friendService;

  /**
   * 유저 본인의 프로필을 생성합니다.
   *
   * @param request 등록할 프로필 JSON 데이터
   * @return 성공한 경우 DB에 저장된 id로 uri 생성
   */
  @PostMapping
  public ResponseEntity<CommonResponse> create(@RequestBody @Valid ProfileRequest request) {
    Long savedId = profileService.save(request);
    log.info("create " + request.getUserId() + " user profile:" + savedId);
    return ResponseEntity.created(URI.create("/api/profile/" + savedId))
        .body(CommonResponse.from(ProfileResponse.PROFILE_CREATE_SUCCESS));
  }

  /**
   * 특정 프로필을 조회합니다.
   *
   * @param profileId 조회할 프로필 id
   * @return 프로필 상세 정보
   */
  @GetMapping("/{profileId}")
  public ResponseEntity<SingleDataResponse<ProfileDetailResponse>> findProfile(
      @PathVariable Long profileId) {
    ProfileDetailResponse profileDetail = profileService.findProfile(profileId);
    SingleDataResponse<ProfileDetailResponse> response = new SingleDataResponse<>(
        ProfileResponse.PROFILE_DETAIL_SUCCESS, profileDetail);
    log.info("read profile" + response.getData().toString());
    return ResponseEntity.ok().body(response);
  }

  /**
   * 유저의 프로필을 모두 조회합니다.
   *
   * @param userId 프로필을 조회할 유저 id
   * @return 유저 정보와 보유한  프로필 리스트
   */
  @GetMapping
  public ResponseEntity<SingleDataResponse<ProfilesResponse>> findProfile(
      @RequestHeader("USER-ID") String userId) {
    ProfilesResponse profiles = profileService.findByUserId(userId);
    SingleDataResponse<ProfilesResponse> response = new SingleDataResponse<>();
    response.success(ProfileResponse.PROFILES_SUCCESS.getMessage(), profiles);
    log.info("read profiles by user id {} ", profiles.getUserId());
    return ResponseEntity.ok().body(response);
  }

  /**
   * 유저 본인의 프로필을 수정합니다.
   *
   * @param profileId 수정할 프로필 id
   * @param request   수정될 프로필 JSON 데이터
   * @return API 성공 여부 전달
   */
  @PutMapping("/{profileId}")
  public ResponseEntity<CommonResponse> updateProfile(@PathVariable Long profileId,
      @RequestBody @Valid ProfileRequest request) {
    Long updatedId = profileService.updateProfile(profileId, request);
    log.info("update " + request.getUserId() + " user profile:" + updatedId);
    int updatedPreviews = friendService.updatePreview(profileId);
    log.info("update {} profile preview", updatedPreviews);
    return ResponseEntity.ok().body(CommonResponse.from(ProfileResponse.PROFILE_UPDATE_SUCCESS));
  }

  /**
   * 요청받은 id에 해당하는 프로필을 삭제합니다.
   *
   * @param profileId 프로필 id
   * @return 삭제 결과에 따른 응답 정보
   */
  @DeleteMapping("/{profileId}")
  public ResponseEntity<CommonResponse> deleteById(@PathVariable Long profileId) {
    profileService.deleteById(profileId);
    log.info("delete profile {}", profileId);

    CommonResponse response = new CommonResponse();
    response.success(ProfileResponse.PROFILE_DELETE_SUCCESS.getMessage());
    return ResponseEntity.ok().body(response);
  }
}
