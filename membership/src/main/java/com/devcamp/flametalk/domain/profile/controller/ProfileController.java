package com.devcamp.flametalk.domain.profile.controller;

import com.devcamp.flametalk.domain.profile.dto.ProfileCreateRequest;
import com.devcamp.flametalk.domain.profile.dto.ProfileResponse;
import com.devcamp.flametalk.domain.profile.dto.ProfileUpdateRequest;
import com.devcamp.flametalk.domain.profile.service.ProfileService;
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
  public ResponseEntity<Void> create(@RequestBody @Valid ProfileCreateRequest request) {
    // TODO: 인가 로직 추후 구현
    Long savedProfileId = profileService.save(request);
    log.info("create " + request.getUserId() + " user profile:" + savedProfileId);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProfileResponse> findProfile(@PathVariable Long id) {
    return ResponseEntity.ok(profileService.findProfile(id));
  }

  @PutMapping("/me")
  public ResponseEntity<Void> updateProfile(@RequestBody @Valid ProfileUpdateRequest request) {
    // TODO: 인가 로직 추후 구현
    Long savedProfileId = profileService.updateProfile(request);
    log.info("update " + request.getUserId() + " user profile:" + savedProfileId);
    return ResponseEntity.ok().build();
  }
}
