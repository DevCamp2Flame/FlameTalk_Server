package com.devcamp.flametalk.token.service;

import static com.devcamp.flametalk.global.error.ErrorCode.TOKEN_NOT_FOUND;

import com.devcamp.flametalk.global.error.exception.CustomException;
import com.devcamp.flametalk.token.domain.TokenRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * Token 비즈니스 로직으로, 사용자가 요청으로 보낸 토큰과 DB에 저장된 토큰이 일치하는지 확인합니다.
 */
@RequiredArgsConstructor
@Service
public class TokenService {

  private final TokenRedisRepository tokenRedisRepository;

  /**
   * 토큰을 발급받은 적이 있다면, accessToken 정보를 갱신하고, 그렇지 않다면 새로운 token 정보를 저장합니다.
   *
   * @param userId           사용자 id
   * @param deviceId         사용자 기기 id
   * @param accessToken      accessToken
   * @param timeoutForAccess accessToken 의 만료 기간 1시간
   */
  public void saveAccessToken(String userId, String deviceId, String accessToken,
      long timeoutForAccess) {
    tokenRedisRepository.saveAccessToken(userId + deviceId, accessToken, timeoutForAccess);
  }

  /**
   * 토큰을 발급받은 적이 있다면, refreshToken 정보를 갱신하고, 그렇지 않다면 새로운 token 정보를 저장합니다.
   *
   * @param userId            사용자 id
   * @param deviceId          사용자 기기 id
   * @param refreshToken      refreshToken
   * @param timeoutForRefresh refreshToken 의 만료 기간 2주
   */
  public void saveRefreshToken(String userId, String deviceId, String refreshToken,
      long timeoutForRefresh) {
    tokenRedisRepository.saveRefreshToken(userId + deviceId, refreshToken, timeoutForRefresh);
  }

  /**
   * DB에 저장된 토큰 값과, header 로 들어온 토큰 값이 일치하는지 확인합니다.
   *
   * @param userId      사용자 id
   * @param deviceId    사용자 기기 id
   * @param accessToken accessToken 만료 기간 1시간
   * @return DB 의 토큰 값과 일치하면 true, 그렇지 않으면 false 반환
   */
  public boolean isEqualPrevTokenForAccess(String userId, String deviceId, String accessToken) {

    String prevAccessToken = tokenRedisRepository.findToken(userId, deviceId, "accessToken");

    if (prevAccessToken == null) {
      throw new CustomException(HttpStatus.NOT_FOUND, TOKEN_NOT_FOUND);
    }

    return prevAccessToken.equals(accessToken);
  }

  /**
   * DB에 저장된 토큰 값과, header 로 들어온 토큰 값이 일치하는지 확인합니다.
   *
   * @param userId       사용자 id
   * @param deviceId     사용자 기기 id
   * @param refreshToken refreshToken 만료 기간 2주
   * @return DB 의 토큰 값과 일치하면 true, 그렇지 않으면 false 반환
   */
  public boolean isEqualPrevTokenForRefresh(String userId, String deviceId, String refreshToken) {

    String prevRefreshToken = tokenRedisRepository.findToken(userId, deviceId, "refreshToken");

    if (prevRefreshToken == null) {
      throw new CustomException(HttpStatus.NOT_FOUND, TOKEN_NOT_FOUND);
    }

    return prevRefreshToken.equals(refreshToken);
  }

  /**
   * 저장된 토큰을 삭제합니다.
   *
   * @param userId   사용자 id
   * @param deviceId 사용자 기기 id
   */
  public void delete(String userId, String deviceId) {
    tokenRedisRepository.deleteToken(userId + deviceId);
  }
}