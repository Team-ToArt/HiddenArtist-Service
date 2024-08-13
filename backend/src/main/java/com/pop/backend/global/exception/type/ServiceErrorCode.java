package com.pop.backend.global.exception.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ServiceErrorCode {

  USER_NOT_FOUND(HttpStatus.NOT_FOUND, "Email에 부합하는 회원이 존재하지 않습니다."),
  PROVIDER_NOT_FOUND(HttpStatus.NOT_FOUND, "OAuth2 Provider를 찾을 수 없습니다."),
  TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "토큰을 찾을 수 없습니다. 로그인해주세요."),
  INVALID_REFRESH_TOKEN(HttpStatus.CONFLICT, "토큰이 일치하지 않습니다. 다시 로그인해주세요."),
  INVALID_ACCESS_TOKEN(HttpStatus.BAD_REQUEST, "토큰이 변조되었습니다. 다시 로그인해주세요."),
  JSON_DESERIALIZE_ERROR(HttpStatus.CONFLICT, "JSON to Object 변환에 실패하였습니다."),
  INVALID_CONTENT_TYPE(HttpStatus.BAD_REQUEST, "올바르지 않은 Content-Type 입니다."),
  BODY_DATA_NOT_FOUND(HttpStatus.NOT_FOUND, "Body 데이터 형식이 올바르지 않습니다."),
  REDIS_VALUE_NOT_FOUND(HttpStatus.NOT_FOUND, "Key와 일치하는 데이터가 존재하지 않습니다."),
  UNLINK_FAIL(HttpStatus.CONFLICT, "OAuth2 연결 끊기에 실패하였습니다."),
  ACCOUNT_ALREADY_DELETED(HttpStatus.BAD_REQUEST, "이미 탈퇴한 계정입니다.");

  private final HttpStatus status;
  private final String errorMessage;

}