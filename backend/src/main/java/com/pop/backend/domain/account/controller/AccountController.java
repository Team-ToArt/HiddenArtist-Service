package com.pop.backend.domain.account.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

  private static final String REDIRECT_URL = "/oauth2/authorization/";
  private final RedirectStrategy redirectStrategy;

  @GetMapping("/signin/{provider}")
  public void redirectOAuth2LoginPage(@PathVariable String provider,
      HttpServletRequest request, HttpServletResponse response) throws IOException {
    String redirectUrl = REDIRECT_URL + provider;
    redirectStrategy.sendRedirect(request, response, redirectUrl);
  }

}