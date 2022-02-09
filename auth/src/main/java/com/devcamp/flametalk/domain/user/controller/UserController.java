package com.devcamp.flametalk.domain.user.controller;

import com.devcamp.flametalk.domain.user.dto.GatewayUserDto;
import com.devcamp.flametalk.domain.user.dto.RenewTokenDto;
import com.devcamp.flametalk.domain.user.dto.SignInRequestDto;
import com.devcamp.flametalk.domain.user.dto.SignInResponseDto;
import com.devcamp.flametalk.domain.user.dto.SignUpRequestDto;
import com.devcamp.flametalk.domain.user.dto.SignUpResponseDto;
import com.devcamp.flametalk.domain.user.service.UserService;
import com.devcamp.flametalk.global.response.DefaultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
  public ResponseEntity<DefaultResponse<SignUpResponseDto>> signUp(
      @RequestBody SignUpRequestDto signUpRequestDto) {
    return userService.signUp(signUpRequestDto);
  }

  @PostMapping("/signin")
  public ResponseEntity<DefaultResponse<SignInResponseDto>> signIn(
      @RequestBody SignInRequestDto signInRequestDto) {
    return userService.signIn(signInRequestDto);
  }

  @DeleteMapping("/leave")
  public ResponseEntity<DefaultResponse<String>> leave(
      @RequestHeader("ACCESS-TOKEN") String token) {
    return userService.leave(token);
  }

  @GetMapping("/token")
  public ResponseEntity<DefaultResponse<RenewTokenDto>> renewToken(
      @RequestHeader("ACCESS-TOKEN") String accessToken,
      @RequestHeader("REFRESH-TOKEN") String refreshToken) {
    return userService.renewToken(accessToken, refreshToken);
  }

  @GetMapping("/check")
  public ResponseEntity<DefaultResponse<Boolean>> checkEmail(@RequestParam("email") String email) {
    return userService.checkEmail(email);
  }

  @GetMapping("/user")
  public GatewayUserDto getUserIfPresent(@RequestHeader("ACCESS-TOKEN") String token) {
    return userService.getUserIfPresent(token);
  }
}