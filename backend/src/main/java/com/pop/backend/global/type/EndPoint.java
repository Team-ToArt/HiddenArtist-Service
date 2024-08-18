package com.pop.backend.global.type;

import org.springframework.http.HttpMethod;

public record EndPoint(
    String pattern,
    HttpMethod method
) {

  public static EndPoint create(String pattern, HttpMethod method) {
    return new EndPoint(pattern, method);
  }

}