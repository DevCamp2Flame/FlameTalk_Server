package com.devcamp.flametalk.global.util;

import static com.devcamp.flametalk.global.error.ErrorCode.REDIRECT_TO_LOGIN;

import com.devcamp.flametalk.global.error.exception.CustomException;
import com.devcamp.flametalk.token.service.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

/**
 * JWT 토큰 생성 및 검증 모듈입니다.
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

  @Value("spring.jwt.secret")
  private String secretKey;
  private long tokenValidMillisecondForAccessToken = 1000L * 60 * 60; // 1시간 동안 토큰 유효
  private long tokenValidMillisecondForRefreshToken = 1000L * 60 * 60 * 24 * 14; // 14일간 토큰 유효
  private String deviceId = "deviceId";
  private String nickname = "nickname";
  private String status = "status";

  @Lazy
  @Autowired
  private UserDetailsService userDetailsService;
  private final TokenService tokenService;

  @PostConstruct
  protected void init() {
    secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
  }

  /**
   * 로그인시 반환되는 Jwt 토큰을 생성합니다.
   *
   * @param userId   사용자 id
   * @param nickname 별명
   * @param status   상태
   * @param deviceId 사용자 기기 id
   * @return 발급된 토큰
   */
  public Map<String, String> createTokenForSignin(String userId, String nickname, String status,
      String deviceId) {

    Claims claims = makeClaims(userId, nickname, status, deviceId);
    Date now = new Date();
    String accessToken = createToken(claims, now, tokenValidMillisecondForAccessToken);
    String refreshToken = createToken(claims, now, tokenValidMillisecondForRefreshToken);

    Map<String, String> tokens = new HashMap<>();
    tokens.put("access_token", accessToken);
    tokens.put("refresh_token", refreshToken);

    tokenService.saveAccessToken(userId, deviceId, accessToken,
        tokenValidMillisecondForAccessToken);
    tokenService.saveRefreshToken(userId, deviceId, refreshToken,
        tokenValidMillisecondForRefreshToken);

    return tokens;
  }

  /**
   * Jwt 토큰으로 인증 정보를 조회합니다.
   *
   * @param token accessToken
   * @return Spring Security 에서 필요한 인증 주체에 대한 토큰
   */
  public Authentication getAuthentication(String token) {
    UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserId(token));
    log.info("인증 정보를 조회" + userDetails.toString());
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  // Request의 Header에서 token 파싱 : "ACCESS-TOKEN: jwt토큰"
  public String resolveAccessToken(HttpServletRequest request) {
    return request.getHeader("ACCESS-TOKEN");
  }

  // Request의 Header에서 token 파싱 : "REFRESH-TOKEN: jwt토큰"
  public String resolveRefreshToken(HttpServletRequest request) {
    return request.getHeader("REFRESH-TOKEN");
  }

  /**
   * Jwt 토큰의 유효성 + 만료일자를 확인합니다.
   *
   * @param token accessToken or refreshToken
   * @return 유효하다면 true, 그렇지 않다면 false
   */
  public boolean validateToken(String token) {
    try {
      Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
      return !claims.getBody().getExpiration().before(new Date());
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * Jwt 토큰의 유효성 + 만료일자가 현재시각으로부터 1주일 이내인지 확인합니다.
   *
   * @param token refreshToken
   * @return 유효하다면 true, 그렇지 않다면 false
   */
  public boolean validateTokenWithinWeek(String token) {
    try {
      Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
      Date expiration = claims.getBody().getExpiration();
      Date now = new Date();
      Calendar laterAweek = Calendar.getInstance();
      laterAweek.setTime(now);
      laterAweek.add(Calendar.DATE, 7);
      // 토큰의 만료 시간이 현재 시간 이후이고, 현재 시간으로부터 7일 후의 값 이전이다.
      return !expiration.before(now) && laterAweek.after(expiration);
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * 토큰을 갱신합니다.
   *
   * @param prevAccessToken  만료된 이전 accessToken
   * @param prevRefreshToken 유효한 refreshToken
   * @return 새로 발급된 토큰
   */
  public Map<String, String> renewToken(String prevAccessToken, String prevRefreshToken) {
    // accessToken 의 유효시간이 아직 남아있거나, DB에 저장된 토큰값과 다르면 탈취되었다고 판단하여 재로그인하게 유도한다.
    String userId = getUserId(prevRefreshToken);
    String deviceId = getDeviceId(prevRefreshToken);

    if (validateToken(prevAccessToken) ||
        !tokenService.isEqualPrevTokenForAccess(userId, deviceId, prevAccessToken) ||
        !tokenService.isEqualPrevTokenForRefresh(userId, deviceId, prevRefreshToken)) {
      tokenService.delete(userId, deviceId);
      throw new CustomException(REDIRECT_TO_LOGIN);
    }

    Date now = new Date();
    String accessToken = createToken(makeClaims(prevRefreshToken), now,
        tokenValidMillisecondForAccessToken);
    tokenService.saveAccessToken(userId, deviceId, accessToken,
        tokenValidMillisecondForAccessToken);

    // refreshToken 만료 기간이 일주일 이내라면 갱신
    String refreshToken = prevRefreshToken;
    if (prevRefreshToken != null && validateTokenWithinWeek(prevRefreshToken)) {
      refreshToken = createToken(makeClaims(prevRefreshToken), now,
          tokenValidMillisecondForRefreshToken);
      tokenService.saveRefreshToken(userId, deviceId, refreshToken,
          tokenValidMillisecondForRefreshToken);
    }

    Map<String, String> tokens = new HashMap<>();
    tokens.put("access_token", accessToken);
    tokens.put("refresh_token", refreshToken);

    return tokens;
  }

  // Jwt 토큰에서 회원 구별 정보 추출
  public String getUserId(String token) {
    return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
  }

  private String getDeviceId(String token) {
    return (String) Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody()
        .get(deviceId);
  }

  private String getNickname(String token) {
    return (String) Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody()
        .get(nickname);
  }

  private String getStatus(String token) {
    return (String) Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody()
        .get(status);
  }

  private Claims makeClaims(String userPk, String nickname, String status, String deviceId) {
    Claims claims = Jwts.claims().setSubject(userPk);
    claims.put(this.nickname, nickname);
    claims.put(this.status, status);
    claims.put(this.deviceId, deviceId);
    return claims;
  }

  private Claims makeClaims(String token) {
    Claims claims = Jwts.claims().setSubject(getUserId(token));
    claims.put(this.nickname, getNickname(token));
    claims.put(this.status, getStatus(token));
    claims.put(this.deviceId, getDeviceId(token));
    return claims;
  }

  private String createToken(Claims claims, Date now, long validMilisecond) {
    return Jwts.builder()
        .setClaims(claims) // 데이터
        .setIssuedAt(now) // 토큰 발행일자
        .setExpiration(
            new Date(now.getTime() + validMilisecond)) // set Expire Time
        .signWith(SignatureAlgorithm.HS256, secretKey) // 암호화 알고리즘, secret값 세팅
        .compact();
  }
}