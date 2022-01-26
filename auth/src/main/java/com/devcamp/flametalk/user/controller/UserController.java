package com.devcamp.flametalk.user.controller;

import static com.devcamp.flametalk.global.response.StatusCode.CREATED_TOKEN;
import static com.devcamp.flametalk.global.response.StatusCode.CREATED_USER;
import static com.devcamp.flametalk.global.response.StatusCode.SUCCESS_LEAVE_USER;
import static com.devcamp.flametalk.global.response.StatusCode.SUCCESS_LOGIN;
import static com.devcamp.flametalk.global.response.StatusCode.VALID_EMAIL;

import com.devcamp.flametalk.global.response.DefaultResponse;
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
    return DefaultResponse.toResponseEntity(HttpStatus.OK, CREATED_USER, userService.signUp(signUpRequestDto));
  }

  @PostMapping("/signin")
  public ResponseEntity<DefaultResponse<SignInResponseDto>> signIn(
      @RequestBody SignInRequestDto signInRequestDto) {
    return DefaultResponse.toResponseEntity(HttpStatus.OK, SUCCESS_LOGIN, userService.signIn(signInRequestDto));
  }

  // todo: 이메일 인증 GetMapping

  @DeleteMapping("/leave")
  public ResponseEntity<DefaultResponse<String>> leave(
      @RequestHeader("ACCESS-TOKEN") String token) {
    return DefaultResponse.toResponseEntity(HttpStatus.OK, SUCCESS_LEAVE_USER, userService.leave(token));
  }

  @GetMapping("/token")
  public ResponseEntity<DefaultResponse<RenewTokenDto>> renewToken(
      @RequestHeader("ACCESS-TOKEN") String accessToken,
      @RequestHeader("REFRESH-TOKEN") String refreshToken) {
    return DefaultResponse.toResponseEntity(HttpStatus.OK, CREATED_TOKEN,
        userService.renewToken(accessToken, refreshToken));
  }

  @GetMapping("/check")
  public ResponseEntity<DefaultResponse<Boolean>> checkEmail(@RequestParam("email") String email) {
    return DefaultResponse.toResponseEntity(HttpStatus.OK, VALID_EMAIL, userService.checkEmail(email));
  }
}