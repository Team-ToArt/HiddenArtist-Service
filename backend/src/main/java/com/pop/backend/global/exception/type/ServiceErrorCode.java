package com.pop.backend.global.exception.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ServiceErrorCode {

  PROVIDER_NOT_FOUND(HttpStatus.NOT_FOUND, "OAuth2 Provider를 찾을 수 없습니다."),
  TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "토큰을 찾을 수 없습니다. 로그인해주세요."),
  INVALID_REFRESH_TOKEN(HttpStatus.CONFLICT, "토큰이 일치하지 않습니다. 다시 로그인해주세요."),
  INVALID_ACCESS_TOKEN(HttpStatus.BAD_REQUEST, "토큰이 변조되었습니다. 다시 로그인해주세요.");

  private final HttpStatus status;
  private final String errorMessage;

}