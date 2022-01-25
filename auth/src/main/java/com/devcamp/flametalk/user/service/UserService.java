package com.devcamp.flametalk.user.service;

import static com.devcamp.flametalk.exception.ErrorCode.DUPLICATE_PHONE_NUMBER;
import static com.devcamp.flametalk.exception.ErrorCode.INVALID_REFRESH_TOKEN;
import static com.devcamp.flametalk.exception.ErrorCode.LEAVE_USER;
import static com.devcamp.flametalk.exception.ErrorCode.MISMATCH_PASSWORD;
import static com.devcamp.flametalk.exception.ErrorCode.USER_NOT_FOUND;

import com.devcamp.flametalk.device.Device;
import com.devcamp.flametalk.device.DeviceRepository;
import com.devcamp.flametalk.exception.CustomException;
import com.devcamp.flametalk.user.domain.Social;
import com.devcamp.flametalk.user.domain.Status;
import com.devcamp.flametalk.user.domain.User;
import com.devcamp.flametalk.user.domain.UserRedisRepository;
import com.devcamp.flametalk.user.domain.UserRepository;
import com.devcamp.flametalk.user.dto.RenewTokenDto;
import com.devcamp.flametalk.user.dto.SignInRequestDto;
import com.devcamp.flametalk.user.dto.SignInResponseDto;
import com.devcamp.flametalk.user.dto.SignUpRequestDto;
import com.devcamp.flametalk.user.dto.SignUpResponseDto;
import com.devcamp.flametalk.token.service.JwtTokenProvider;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    return userRepository.findById(userId).orElseThrow(
        () -> new CustomException(USER_NOT_FOUND));
  }

  /**
   * 회원가입 메소드입니다.
   *
   * @param signUpRequestDto 회원가입 요청 dto
   * @return 생성된 사용자 정보
   */
  public SignUpResponseDto signUp(SignUpRequestDto signUpRequestDto) {
    if (userRepository.findByPhoneNumber(signUpRequestDto.getPhoneNumber()).isPresent()) {
      throw new CustomException(DUPLICATE_PHONE_NUMBER);
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

    return new SignUpResponseDto(user);
  }

  /**
   * 로그인 메소드입니다.
   *
   * @param signInRequestDto 사용자 로그인 정보
   * @return 사용자 정보와 토큰 정보
   */
  public SignInResponseDto signIn(SignInRequestDto signInRequestDto) {
    // 등록된 사용자가 아님
    User user = userRepository.findByEmail(signInRequestDto.getEmail())
        .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

    // 비밀번호 불일치
    if (signInRequestDto.getSocial().equals(Social.LOGIN.getName()) && !passwordEncoder.matches(
        signInRequestDto.getPassword(), user.getPassword())) {
      throw new CustomException(MISMATCH_PASSWORD);
    }

    // 상태가 LEAVE 이면서 캐시에 데이터가 존재하면 유휴 기간. 유휴 기간에 재로그인하면 active 상태로 변경
    // 캐시에 데이터가 없다면 탈퇴한 사용자
    if (user.getStatus().equals(Status.LEAVE)) {
      String date = userRedisRepository.findLeaveDate(user.getId());
      if (date != null) {
        user.activeStatus();
        userRepository.save(user);
      } else {
        throw new CustomException(LEAVE_USER);
      }
    }

    Map<String, String> tokens = jwtTokenProvider.createTokenForSignin(user.getId(),
        user.getNickname(),
        user.getStatus().getName(), signInRequestDto.getDeviceId());

    return new SignInResponseDto(user, tokens);
  }

  /**
   * 탈퇴 메소드입니다.
   *
   * @param token accessToken 만료 기간 1시간
   * @return 탈퇴 완료 메시지
   */
  public String leave(String token) {
    User user = userRepository.findById(jwtTokenProvider.getUserId(token))
        .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

    // 비활성 상태로 변경
    user.inactiveStatus();
    userRepository.save(user);

    // 유저 캐싱
    userRedisRepository.saveIdleUser(user.getId());

    return "탈퇴 완료 : " + user.getNickname() + "님 30일 이내에 재접속하면 탈퇴되지 않습니다.";
  }

  /**
   * 토큰 갱신 메소드입니다.
   *
   * @param accessToken  accessToken 만료 기간 1시간
   * @param refreshToken refreshToken 만료 기간 2주
   * @return 갱신된 토큰
   */
  public RenewTokenDto renewToken(String accessToken, String refreshToken) {
    if (!jwtTokenProvider.validateToken(refreshToken)) {
      throw new CustomException(INVALID_REFRESH_TOKEN);
    }

    User user = userRepository.findById(jwtTokenProvider.getUserId(refreshToken))
        .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

    Map<String, String> tokens = jwtTokenProvider.renewToken(accessToken, refreshToken);

    return new RenewTokenDto(user, tokens);
  }
}