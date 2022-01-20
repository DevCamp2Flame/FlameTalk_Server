package com.devcamp.flametalk.token.service;

import com.devcamp.flametalk.exception.CustomException;
import com.devcamp.flametalk.exception.ErrorCode;
import com.devcamp.flametalk.token.domain.Token;
import com.devcamp.flametalk.token.domain.TokenRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Token 비즈니스 로직으로, 사용자가 요청으로 보낸 토큰과 DB에 저장된 토큰이 일치하는지 확인합니다.
 */
@RequiredArgsConstructor
@Service
public class TokenService {

  private final TokenRepository tokenRepository;

  /**
   * 토큰을 발급받은 적이 있다면, 해당 객체를 찾아 갱신하고, 그렇지 않다면 새로운 객체를 생성하여 저장합니다.
   *
   * @param userId       사용자 id
   * @param deviceId     사용자 기기 id
   * @param accessToken  accessToken 만료 기간 1시간
   * @param refreshToken refreshToken 만료 기간 2주
   */
  public void save(String userId, String deviceId, String accessToken, String refreshToken) {
    Optional<Token> optionalToken = tokenRepository.findByDeviceId(deviceId);

    Token token;
    if (optionalToken.isPresent()) {
      token = optionalToken.get();
      token.setToken(accessToken, refreshToken);
    } else {
      token = Token.builder()
          .userId(userId)
          .deviceId(deviceId)
          .accessToken(accessToken)
          .refreshToken(refreshToken)
          .build();
    }

    tokenRepository.save(token);
  }

  /**
   * DB에 저장된 토큰 값과, header 로 들어온 토큰 값이 일치하는지 확인합니다.
   *
   * @param deviceId     사용자 기기 id
   * @param accessToken  accessToken 만료 기간 1시간
   * @param refreshToken refreshToken 만료 기간 2주
   * @return DB 의 토큰 값과 일치하면 true, 그렇지 않으면 false 반환
   */
  public boolean isEqualPrevToken(String deviceId, String accessToken, String refreshToken) {
    Token token = tokenRepository.findByDeviceId(deviceId).orElseThrow(() -> new CustomException(
        ErrorCode.TOKEN_NOT_FOUND));

    return token.getAccessToken().equals(accessToken) && token.getRefreshToken()
        .equals(refreshToken);
  }
}