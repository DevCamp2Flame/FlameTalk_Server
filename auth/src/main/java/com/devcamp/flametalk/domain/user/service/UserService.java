package com.devcamp.flametalk.user.service;

import static com.devcamp.flametalk.global.error.ErrorCode.USER_NOT_FOUND_BY_TOKEN;
import static com.devcamp.flametalk.global.response.StatusCode.CREATED_TOKEN;
import static com.devcamp.flametalk.global.response.StatusCode.CREATED_USER;
import static com.devcamp.flametalk.global.response.StatusCode.DUPLICATE_EMAIL;
import static com.devcamp.flametalk.global.response.StatusCode.DUPLICATE_PHONE_NUMBER;
import static com.devcamp.flametalk.global.response.StatusCode.INVALID_TOKEN;
import static com.devcamp.flametalk.global.response.StatusCode.LEAVE_USER;
import static com.devcamp.flametalk.global.response.StatusCode.MISMATCH_PASSWORD;
import static com.devcamp.flametalk.global.response.StatusCode.REDIRECT_TO_LOGIN;
import static com.devcamp.flametalk.global.response.StatusCode.SUCCESS_LEAVE;
import static com.devcamp.flametalk.global.response.StatusCode.SUCCESS_LOGIN;
import static com.devcamp.flametalk.global.response.StatusCode.USER_NOT_FOUND;
import static com.devcamp.flametalk.global.response.StatusCode.VALID_EMAIL;

import com.devcamp.flametalk.device.Device;
import com.devcamp.flametalk.device.DeviceRepository;
import com.devcamp.flametalk.global.error.exception.CustomException;
import com.devcamp.flametalk.global.response.DefaultResponse;
import com.devcamp.flametalk.global.util.JwtTokenProvider;
import com.devcamp.flametalk.user.domain.Social;
import com.devcamp.flametalk.user.domain.Status;
import com.devcamp.flametalk.user.domain.User;
import com.devcamp.flametalk.user.domain.UserRedisRepository;
import com.devcamp.flametalk.user.domain.UserRepository;
import com.devcamp.flametalk.user.dto.GatewayUserDto;
import com.devcamp.flametalk.user.dto.RenewTokenDto;
import com.devcamp.flametalk.user.dto.SignInRequestDto;
import com.devcamp.flametalk.user.dto.SignInResponseDto;
import com.devcamp.flametalk.user.dto.SignUpRequestDto;
import com.devcamp.flametalk.user.dto.SignUpResponseDto;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * User 의 비즈니스 로직을 처리하는 클래스입니다.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

  @Autowired
  private JwtTokenProvider jwtTokenProvider;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final UserRedisRepository userRedisRepository;
  private final DeviceRepository deviceRepository;

  @Override
  public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
    return userRepository.findById(userId)
        .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, USER_NOT_FOUND_BY_TOKEN));
  }

  /**
   * 회원가입 메소드입니다.
   *
   * @param signUpRequestDto 회원가입 요청 dto
   * @return 생성된 사용자 정보
   */
  public ResponseEntity<DefaultResponse<SignUpResponseDto>> signUp(
      SignUpRequestDto signUpRequestDto) {
    if (userRepository.findByPhoneNumber(signUpRequestDto.getPhoneNumber()).isPresent()) {
      return DefaultResponse.toResponseEntity(HttpStatus.OK, DUPLICATE_PHONE_NUMBER,
          new SignUpResponseDto());
    }

    String password = "";
    if (!signUpRequestDto.getPassword().isEmpty()) {
      password = passwordEncoder.encode(signUpRequestDto.getPassword());
    }

    User user = userRepository.save(signUpRequestDto.toEntity(password));

    // device 정보 추가
    deviceRepository.save(Device.builder()
        .user(user)
        .deviceId(signUpRequestDto.getDeviceId())
        .build()
    );

    return DefaultResponse.toResponseEntity(HttpStatus.OK, CREATED_USER,
        new SignUpResponseDto(user));
  }

  /**
   * 로그인 메소드입니다.
   *
   * @param signInRequestDto 사용자 로그인 정보
   * @return 사용자 정보와 토큰 정보
   */
  public ResponseEntity<DefaultResponse<SignInResponseDto>> signIn(
      SignInRequestDto signInRequestDto) {
    // 등록된 사용자가 아님
    User user = userRepository.findByEmail(signInRequestDto.getEmail()).orElse(null);
    if (user == null) {
      return DefaultResponse.toResponseEntity(HttpStatus.OK, USER_NOT_FOUND,
          new SignInResponseDto());
    }

    // 비밀번호 불일치
    if (signInRequestDto.getSocial().equals(Social.LOGIN.getName()) && !passwordEncoder.matches(
        signInRequestDto.getPassword(), user.getPassword())) {
      return DefaultResponse.toResponseEntity(HttpStatus.OK, MISMATCH_PASSWORD,
          new SignInResponseDto());
    }

    // 상태가 LEAVE 이면서 캐시에 데이터가 존재하면 유휴 기간. 유휴 기간에 재로그인하면 active 상태로 변경
    // 캐시에 데이터가 없다면 탈퇴한 사용자
    if (user.getStatus().equals(Status.LEAVE)) {
      String date = userRedisRepository.findLeaveDate(user.getId());
      if (date != null) {
        user.activeStatus();
        userRepository.save(user);
      } else {
        return DefaultResponse.toResponseEntity(HttpStatus.OK, LEAVE_USER, new SignInResponseDto());
      }
    }

    Map<String, String> tokens = jwtTokenProvider.createTokenForSignin(user.getId(),
        user.getNickname(),
        user.getStatus().getName(), signInRequestDto.getDeviceId());

    return DefaultResponse.toResponseEntity(HttpStatus.OK, SUCCESS_LOGIN,
        new SignInResponseDto(user, tokens));
  }

  /**
   * 탈퇴 메소드입니다.
   *
   * @param token accessToken 만료 기간 1시간
   * @return 탈퇴 완료 메시지
   */
  public ResponseEntity<DefaultResponse<String>> leave(String token) {
    if (!jwtTokenProvider.isEqualPrevTokenForAccess(token)) {
      return DefaultResponse.toResponseEntity(HttpStatus.OK, INVALID_TOKEN, "");
    }

    User user = userRepository.findById(jwtTokenProvider.getUserId(token)).orElse(null);
    if (user == null) {
      return DefaultResponse.toResponseEntity(HttpStatus.OK, USER_NOT_FOUND, "");
    }

    // 비활성 상태로 변경
    user.inactiveStatus();
    userRepository.save(user);

    // 유저 캐싱
    userRedisRepository.saveIdleUser(user.getId());

    return DefaultResponse.toResponseEntity(HttpStatus.OK, SUCCESS_LEAVE,
        user.getNickname() + "님 30일 이내에 재접속하면 탈퇴되지 않습니다.");
  }

  /**
   * 토큰 갱신 메소드입니다.
   *
   * @param accessToken  accessToken 만료 기간 1시간
   * @param refreshToken refreshToken 만료 기간 2주
   * @return 갱신된 토큰
   */
  public ResponseEntity<DefaultResponse<RenewTokenDto>> renewToken(String accessToken,
      String refreshToken) {
    User user = userRepository.findById(jwtTokenProvider.getUserId(refreshToken)).orElse(null);
    if (user == null) {
      return DefaultResponse.toResponseEntity(HttpStatus.OK, USER_NOT_FOUND, new RenewTokenDto());
    }

    Map<String, String> tokens = jwtTokenProvider.renewToken(accessToken, refreshToken);
    if (tokens == null) {
      return DefaultResponse.toResponseEntity(HttpStatus.OK, REDIRECT_TO_LOGIN,
          new RenewTokenDto());
    }

    return DefaultResponse.toResponseEntity(HttpStatus.OK, CREATED_TOKEN,
        new RenewTokenDto(user, tokens));
  }

  /**
   * 회원가입할 때 중복된 이메일이 있는지 확인합니다.
   *
   * @param email 이메일
   * @return 중복된 이메일이 아니라면 true, 회원가입된 이메일이라면 false
   */
  public ResponseEntity<DefaultResponse<Boolean>> checkEmail(String email) {
    if (userRepository.findByEmail(email).isPresent()) {
      return DefaultResponse.toResponseEntity(HttpStatus.OK, DUPLICATE_EMAIL, false);
    }
    return DefaultResponse.toResponseEntity(HttpStatus.OK, VALID_EMAIL, true);
  }

  /**
   * Gateway 에서 요청한 토큰이 올바른 토큰인지 확인하고, 토큰 정보와 일치하는 사용자 정보를 가져오기 위한 메소드입니다.
   *
   * @param token accessToken
   * @return GatewayUserDto or null
   */
  public GatewayUserDto getUserIfPresent(String token) {
    // 올바른 토큰인지 확인
    if (!jwtTokenProvider.validateToken(token)) {
      return null;
    }

    // token DB 에 저장된 토큰 내용과 일치하는지 확인
    if (!jwtTokenProvider.isEqualPrevTokenForAccess(token)) {
      return null;
    }

    // 토큰 정보와 일치하는 사용자가 있는지 확인
    User user = userRepository.findById(jwtTokenProvider.getUserId(token)).orElse(null);
    if (user == null) {
      return null;
    }

    return new GatewayUserDto(user.getId(), jwtTokenProvider.getDeviceId(token));
  }
}