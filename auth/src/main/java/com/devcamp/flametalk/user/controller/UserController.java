package com.devcamp.flametalk.user.controller;

import com.devcamp.flametalk.user.dto.RenewTokenDto;
import com.devcamp.flametalk.user.dto.SignInRequestDto;
import com.devcamp.flametalk.user.dto.SignInResponseDto;
import com.devcamp.flametalk.user.dto.SignUpRequestDto;
import com.devcamp.flametalk.user.dto.SignUpResponseDto;
import com.devcamp.flametalk.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 사용자 인증과 관련된 컨트롤러입니다.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class UserController {

  private final UserService userService;

  @PostMapping("/signup")
  public ResponseEntity<SignUpResponseDto> signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
    return ResponseEntity.status(HttpStatus.OK).body(userService.signUp(signUpRequestDto));
  }

  @PostMapping("/signin")
  public ResponseEntity<SignInResponseDto> signIn(@RequestBody SignInRequestDto signInRequestDto) {
    return ResponseEntity.status(HttpStatus.OK).body(userService.signIn(signInRequestDto));
  }

  // todo: 이메일 확인 GetMapping

  @DeleteMapping("/leave")
  public ResponseEntity<String> leave(@RequestHeader("ACCESS-TOKEN") String token) {
    return ResponseEntity.status(HttpStatus.OK).body(userService.leave(token));
  }

  @GetMapping("/token")
  public ResponseEntity<RenewTokenDto> renewToken(@RequestHeader("ACCESS-TOKEN") String accessToken,
      @RequestHeader("REFRESH-TOKEN") String refreshToken) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(userService.renewToken(accessToken, refreshToken));
  }
}