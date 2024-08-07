package com.pop.backend.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ServiceErrorCode {

  TEST(HttpStatus.BAD_REQUEST, "테스트 예외상황");

  private final HttpStatus status;
  private final String errorMessage;

}