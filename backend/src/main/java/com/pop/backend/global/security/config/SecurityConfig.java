package com.pop.backend.global.security.config;

import com.pop.backend.global.security.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final CustomOAuth2UserService oAuth2UserService;
  private final SecurityHandlerConfig handlerConfig;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http.csrf(AbstractHttpConfigurer::disable)
        .cors(AbstractHttpConfigurer::disable)
        .formLogin(AbstractHttpConfigurer::disable);

    http.sessionManagement(sessionConfigurer -> sessionConfigurer
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

    http.authorizeHttpRequests(requestRegistry -> requestRegistry
        .anyRequest().permitAll());

    http.oauth2Login(oauth2LoginConfigurer -> oauth2LoginConfigurer
        .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig
            .userService(oAuth2UserService))
        .successHandler(handlerConfig.getOAuth2LoginSuccessHandler()));

    return http.build();
  }

}