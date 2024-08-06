package com.pop.backend.global.security.handler;

import com.pop.backend.global.jwt.TokenService;
import com.pop.backend.global.utils.CookieManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

  private final String COOKIE_NAME = "AccessToken";
  private final String REDIRECT_URL = "http://localhost/login-success";
  private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
  private final TokenService tokenService;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {
    String accessToken = tokenService.createJWT(authentication);
    CookieManager.saveCookie(COOKIE_NAME, accessToken, response);
    redirectStrategy.sendRedirect(request, response, REDIRECT_URL);
  }

}