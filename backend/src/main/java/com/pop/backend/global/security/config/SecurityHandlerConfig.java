package com.pop.backend.global.security.config;

import com.pop.backend.global.security.handler.OAuth2LoginSuccessHandler;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
@RequiredArgsConstructor
public class SecurityHandlerConfig {

  private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;

}