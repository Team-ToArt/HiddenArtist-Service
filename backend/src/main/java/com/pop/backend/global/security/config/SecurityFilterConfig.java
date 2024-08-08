package com.pop.backend.global.security.config;

import com.pop.backend.global.security.filter.JWTAuthenticationFilter;
import com.pop.backend.global.security.filter.SecurityExceptionHandleFilter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
@RequiredArgsConstructor
public class SecurityFilterConfig {

  private final JWTAuthenticationFilter jwtAuthenticationFilter;

  private final SecurityExceptionHandleFilter exceptionHandleFilter;

}