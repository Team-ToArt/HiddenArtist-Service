package com.pop.backend.global.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

  private final JWTProvider jwtProvider;
  private final RefreshTokenService refreshTokenService;

  public String createJWT(Authentication authentication) {
    GenerateToken generateToken = jwtProvider.generateTokens(authentication);
    refreshTokenService.save(generateToken.refreshToken());
    return generateToken.accessToken();
  }

}