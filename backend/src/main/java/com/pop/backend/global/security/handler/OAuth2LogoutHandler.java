package com.pop.backend.global.security.handler;

import com.pop.backend.global.jwt.TokenService;
import com.pop.backend.global.type.CookieNames;
import com.pop.backend.global.utils.CookieManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OAuth2LogoutHandler implements LogoutHandler {

  private final TokenService tokenService;

  @Override
  public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
    CookieManager.removeCookie(CookieNames.ACCESS_TOKEN.getName(), response);
    CookieManager.removeCookie(CookieNames.REFRESH_TOKEN.getName(), response);

    String email = (String) authentication.getPrincipal();
    tokenService.removeRefreshToken(email);
  }

}