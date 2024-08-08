package com.pop.backend.global.security.filter;

import com.pop.backend.global.exception.type.SecurityException;
import com.pop.backend.global.exception.type.ServiceErrorCode;
import com.pop.backend.global.jwt.GenerateToken;
import com.pop.backend.global.jwt.TokenService;
import com.pop.backend.global.type.CookieNames;
import com.pop.backend.global.type.JWTValidationResult;
import com.pop.backend.global.utils.CookieManager;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {

  private final TokenService tokenService;
  private final PathMatcher pathMatcher = new AntPathMatcher();
  private final List<EndPoint> tokenFreeEndPoints = List.of(
      // 임시로 작성한 EndPoint 입니다.
      new EndPoint("/api/accounts/signup", HttpMethod.POST),
      new EndPoint("/api/accounts/signin", HttpMethod.POST)
  );

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    if (isTokenFreeEndPoint(request)) {
      doFilter(request, response, filterChain);
      return;
    }

    String accessToken = CookieManager.getCookie(CookieNames.ACCESS_TOKEN.getName(), request);

    JWTValidationResult validationResult = tokenService.validateToken(accessToken);
    switch (validationResult) {
      case VALID -> {
        tokenService.saveAuthentication(accessToken);
        filterChain.doFilter(request, response);
      }
      case EXPIRED -> {
        String refreshToken = CookieManager.getCookie(CookieNames.REFRESH_TOKEN.getName(), request);
        refreshToken(refreshToken, response);
        filterChain.doFilter(request, response);
      }
      case INVALID -> throw new SecurityException(ServiceErrorCode.INVALID_ACCESS_TOKEN);
    }
  }

  private boolean isTokenFreeEndPoint(HttpServletRequest request) {
    String requestUri = request.getRequestURI();
    String requestMethod = request.getMethod().toUpperCase();
    return tokenFreeEndPoints.stream().anyMatch(endPoint -> isMatches(endPoint, requestUri, requestMethod));
  }

  private boolean isMatches(EndPoint endPoint, String requestUri, String httpMethod) {
    return pathMatcher.match(endPoint.pattern(), requestUri) && endPoint.method().matches(httpMethod);
  }

  private void refreshToken(String token, HttpServletResponse response) throws SecurityException {
    JWTValidationResult validationResult = tokenService.validateToken(token);
    if (validationResult.isNotValid()) {
      throw new SecurityException(ServiceErrorCode.INVALID_REFRESH_TOKEN);
    }
    GenerateToken generateToken = tokenService.refreshJWT(token);
    CookieManager.storeTokenInCookie(generateToken, response);
  }

  private record EndPoint(
      String pattern,
      HttpMethod method
  ) {

  }
}