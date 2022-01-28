package com.devcamp.flametalk.domain.profile.controller;

import com.devcamp.flametalk.domain.profile.domain.ProfileResponse;
import com.devcamp.flametalk.domain.profile.dto.CommonResponse;
import com.devcamp.flametalk.domain.profile.dto.ProfileDetailResponse;
import com.devcamp.flametalk.domain.profile.dto.ProfileRequest;
import com.devcamp.flametalk.domain.profile.dto.SingleDataResponse;
import com.devcamp.flametalk.domain.profile.service.ProfileService;
import java.net.URI;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Membership 서버의 Profile API 처리 컨트롤러입니다.
 */
@Slf4j
@CrossOrigin("*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/profile")
public class ProfileController {

  private final ProfileService profileService;

  /**
   * 유저 본인의 프로필을 수정합니다.
   *
   * @param request 등록할 프로필 JSON 데이터
   * @return 성공한 경우 DB에 저장된 id로 uri 생성
   */
  @PostMapping("/me")
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
   * 유저 본인의 프로필을 수정합니다.
   *
   * @param profileId 수정할 프로필 id
   * @param request   수정될 프로필 JSON 데이터
   * @return API 성공 여부 전달
   */
  @PutMapping("/me/{profileId}")
  public ResponseEntity<CommonResponse> updateProfile(@PathVariable Long profileId,
      @RequestBody @Valid ProfileRequest request) {
    Long updatedId = profileService.updateProfile(profileId, request);
    log.info("update " + request.getUserId() + " user profile:" + updatedId);
    return ResponseEntity.ok().body(CommonResponse.from(ProfileResponse.PROFILE_UPDATE_SUCCESS));
  }
}
