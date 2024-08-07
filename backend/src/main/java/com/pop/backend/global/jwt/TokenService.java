package com.pop.backend.global.jwt;

import com.pop.backend.global.type.JWTValidationResult;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

  private final JWTProvider jwtProvider;
  private final RefreshTokenService refreshTokenService;

  public GenerateToken createJWT(String email, String authorities) {
    GenerateToken generateToken = jwtProvider.generateToken(email, authorities);
    refreshTokenService.save(generateToken.refreshToken());
    return generateToken;
  }

  public GenerateToken refreshJWT(String token) {
    String email = jwtProvider.getEmail(token);
    RefreshToken refreshToken = refreshTokenService.findRefreshTokenBy(email);
    if (!token.equals(refreshToken.getRefreshToken())) {
      throw new RuntimeException("리프레시 토큰이 동일하지 않습니다. 다시 로그인해주세요.");
    }
    String authorities = jwtProvider.getAuthorities(token);
    return createJWT(email, authorities);
  }

  public JWTValidationResult validateToken(String token) {
    return jwtProvider.verifyToken(token);
  }

  public void saveAuthentication(String token) {
    String email = jwtProvider.getEmail(token);
    String role = jwtProvider.getAuthorities(token);
    List<SimpleGrantedAuthority> authorities = Arrays.stream(role.split(",")).map(SimpleGrantedAuthority::new).toList();
    Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, authorities);
    SecurityContextHolder.getContext().setAuthentication(authentication);
  }

  public void removeRefreshToken(String email) {
    refreshTokenService.removeRefreshToken(email);
  }

}