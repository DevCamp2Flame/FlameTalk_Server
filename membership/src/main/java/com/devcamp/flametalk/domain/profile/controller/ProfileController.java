package com.devcamp.flametalk.domain.profile.controller;

import com.devcamp.flametalk.domain.profile.dto.ProfileCreateRequest;
import com.devcamp.flametalk.domain.profile.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
}
