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

@Slf4j
@CrossOrigin("*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/profile")
public class ProfileController {

  private final ProfileService profileService;

  @PostMapping("/me")
  public ResponseEntity<CommonResponse> create(@RequestBody @Valid ProfileRequest request) {
    Long savedId = profileService.save(request);
    log.info("create " + request.getUserId() + " user profile:" + savedId);
    return ResponseEntity.created(URI.create("/api/profile/" + savedId))
        .body(CommonResponse.from(ProfileResponse.PROFILE_CREATE_SUCCESS));
  }

  @GetMapping("/{profileId}")
  public ResponseEntity<SingleDataResponse> findProfile(@PathVariable Long profileId) {
    ProfileDetailResponse profileDetail = profileService.findProfile(profileId);
    SingleDataResponse<ProfileDetailResponse> response = new SingleDataResponse(
        ProfileResponse.PROFILE_DETAIL_SUCCESS, profileDetail);
    log.info("read profile" + response.getData().toString());
    return ResponseEntity.ok().body(response);
  }

  @PutMapping("/me/{profileId}")
  public ResponseEntity<CommonResponse> updateProfile(@PathVariable Long profileId,
      @RequestBody @Valid ProfileRequest request) {
    Long updatedId = profileService.updateProfile(profileId, request);
    log.info("update " + request.getUserId() + " user profile:" + updatedId);
    return ResponseEntity.ok().body(CommonResponse.from(ProfileResponse.PROFILE_UPDATE_SUCCESS));
  }
}
