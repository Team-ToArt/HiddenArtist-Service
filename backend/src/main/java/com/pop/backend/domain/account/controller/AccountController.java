package com.pop.backend.domain.account.controller;

import com.pop.backend.domain.account.service.AccountService;
import com.pop.backend.global.type.CookieNames;
import com.pop.backend.global.utils.CookieManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

  private static final String REDIRECT_URL = "/oauth2/authorization/";
  private final AccountService accountService;

  @GetMapping("/signin/{provider}")
  public void redirectOAuth2LoginPage(@PathVariable String provider, HttpServletResponse response) throws IOException {
    String redirectUrl = REDIRECT_URL + provider;
    response.sendRedirect(redirectUrl);
  }

  @DeleteMapping("/withdraw")
  public void withDrawAccount(@AuthenticationPrincipal String email,
      HttpServletRequest request, HttpServletResponse response) {
    String refreshToken = CookieManager.getCookie(CookieNames.REFRESH_TOKEN.getName(), request);
    accountService.withDrawAccount(email, refreshToken);
    CookieManager.removeCookie(CookieNames.ACCESS_TOKEN.getName(), response);
    CookieManager.removeCookie(CookieNames.REFRESH_TOKEN.getName(), response);
  }

}