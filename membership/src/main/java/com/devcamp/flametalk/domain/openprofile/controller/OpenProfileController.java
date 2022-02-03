package com.devcamp.flametalk.domain.openprofile.controller;

import com.devcamp.flametalk.domain.openprofile.domain.OpenProfileResponse;
import com.devcamp.flametalk.domain.openprofile.dto.OpenProfileCreateRequest;
import com.devcamp.flametalk.domain.openprofile.dto.OpenProfileDetailResponse;
import com.devcamp.flametalk.domain.openprofile.dto.OpenProfileUpdateRequest;
import com.devcamp.flametalk.domain.openprofile.service.OpenProfileService;
import com.devcamp.flametalk.global.common.CommonResponse;
import com.devcamp.flametalk.global.common.SingleDataResponse;
import java.net.URI;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Membership 서버의 OpenProfile API 처리 컨트롤러입니다.
 */
@Slf4j
@CrossOrigin("*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/membership/open-profile")
public class OpenProfileController {

  private final OpenProfileService openProfileService;

  /**
   * 오픈 프로필을 생성합니다.
   *
   * @param request 등록할 오픈 프로필 JSON 데이터
   * @return 생성된 오픈 프로필 상세 정보
   */
  @PostMapping
  public ResponseEntity<SingleDataResponse<OpenProfileDetailResponse>> create(
      @RequestBody @Valid OpenProfileCreateRequest request) {
    OpenProfileDetailResponse openProfileDetail = openProfileService.save(request);
    SingleDataResponse<OpenProfileDetailResponse> response = new SingleDataResponse<>();
    response.success(OpenProfileResponse.OPEN_PROFILE_CREATE_SUCCESS.getMessage(),
        openProfileDetail);
    log.info("create open profile {} ", openProfileDetail.getOpenProfileId());
    return ResponseEntity.created(
            URI.create("/api/membership/open-profile" + openProfileDetail.getOpenProfileId()))
        .body(response);
  }

  /**
   * 오픈 프로필을 수정합니다.
   *
   * @param openProfileId 수정할 오픈 프로필 id
   * @param request       수정될 오픈 프로필 JSON 데이터
   * @return 수정된 오픈 프로필 상세 정보
   */
  @PutMapping("/{openProfileId}")
  public ResponseEntity<SingleDataResponse<OpenProfileDetailResponse>> update(
      @PathVariable Long openProfileId, @RequestBody @Valid OpenProfileUpdateRequest request) {
    OpenProfileDetailResponse openProfileDetail = openProfileService.updateOpenProfile(
        openProfileId, request);
    SingleDataResponse<OpenProfileDetailResponse> response = new SingleDataResponse<>();
    response.success(OpenProfileResponse.OPEN_PROFILE_UPDATE_SUCCESS.getMessage(),
        openProfileDetail);
    log.info("update open profile {} ", openProfileDetail.getOpenProfileId());
    return ResponseEntity.ok().body(response);
  }

  /**
   * 요청받은 id에 해당하는 오픈 프로필을 삭제합니다.
   *
   * @param openProfileId 오픈 프로필 id
   * @return 삭제 결과에 따른 응답 정보
   */
  @DeleteMapping("/{openProfileId}")
  public ResponseEntity<CommonResponse> deleteById(@PathVariable Long openProfileId) {
    openProfileService.deleteById(openProfileId);
    log.info("delete feed {}", openProfileId);

    CommonResponse response = new CommonResponse();
    response.success(OpenProfileResponse.OPEN_PROFILE_DELETE_SUCCESS.getMessage());
    return ResponseEntity.ok().body(response);
  }
}
