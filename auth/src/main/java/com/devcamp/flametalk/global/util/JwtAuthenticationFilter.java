package com.devcamp.flametalk.global.util;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

/**
 * Request 로 들어오는 Jwt 토큰을 검증하는 필터입니다.
 */
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

  private JwtTokenProvider jwtTokenProvider;

  // JwtTokenProvier 주입
  public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
    this.jwtTokenProvider = jwtTokenProvider;
  }

  // todo : gateway filter 로 옮겨 완성하기
  // Jwt Token의 유효성을 검증(jwtTokenProvider.validateToken)하는 filter를 filterChain에 등록한다.
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
      throws IOException, ServletException {

    String accessToken = jwtTokenProvider.resolveAccessToken((HttpServletRequest) request);

    if (accessToken != null && jwtTokenProvider.validateToken(accessToken)) {
      // 1. accessToken 이 유효한 경우
      Authentication auth = jwtTokenProvider.getAuthentication(accessToken);
      SecurityContextHolder.getContext().setAuthentication(auth);
    } else {
      // 2. accessToken 이 유효하지 않은 경우
      String refreshToken = jwtTokenProvider.resolveRefreshToken((HttpServletRequest) request);
      if (refreshToken != null && jwtTokenProvider.validateToken(refreshToken)) {
        // 2-1. refreshToken 을 확인하여 만료되지 않았다면 token 을 재발급하도록 메시지를 리턴한다.
      } else {
        // 2-2. refreshToken 가 만료되어 다시 로그인하도록 메시지를 리턴한다.
      }
    }
    filterChain.doFilter(request, response); // 체인을 따라서 다음 필터로 이동
  }
}