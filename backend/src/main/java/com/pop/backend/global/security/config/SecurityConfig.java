package com.pop.backend.global.security.config;

import com.pop.backend.domain.account.persistence.type.Role;
import com.pop.backend.global.security.filter.JSONUsernamePasswordAuthenticationFilter;
import com.pop.backend.global.security.filter.JWTAuthenticationFilter;
import com.pop.backend.global.security.service.CustomOAuth2UserService;
import com.pop.backend.global.type.EndPoint;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final List<EndPoint> adminPermitAllEndPoints = List.of(
      EndPoint.create("/admin/signin", HttpMethod.GET),
      EndPoint.create("/admin/accounts/signup", HttpMethod.POST)
  );

  private final List<EndPoint> apiPermitAllEndPoints = List.of(
      EndPoint.create("/api/accounts/signin/{provider}", HttpMethod.GET),
      EndPoint.create("/api/artists", HttpMethod.GET),
      EndPoint.create("/api/artists/{token}", HttpMethod.GET),
      EndPoint.create("/api/artists/popular", HttpMethod.GET)
  );

  private final CustomOAuth2UserService oAuth2UserService;
  private final SecurityHandlerConfig handlerConfig;
  private final SecurityFilterConfig filterConfig;

  @Bean
  @Order(1)
  public SecurityFilterChain adminSecurityFilterChain(HttpSecurity http) throws Exception {
    http.securityMatcher("/admin", "/admin/**");

    http.csrf(AbstractHttpConfigurer::disable)
        .cors(AbstractHttpConfigurer::disable)
        .formLogin(AbstractHttpConfigurer::disable);

    http.exceptionHandling(exceptionHandling -> exceptionHandling
        .accessDeniedHandler(handlerConfig.getAdminAccessDeniedHandler())
        .authenticationEntryPoint(handlerConfig.getAdminAuthenticationEntryPoint()));

    configureAdminEndpoints(http);

    http.addFilterBefore(filterConfig.getUsernamePasswordAuthenticationFilter(),
            UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(filterConfig.getExceptionHandleFilter(), JSONUsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

  @Bean
  @Order(2)
  public SecurityFilterChain apiSecurityFilterChain(HttpSecurity http) throws Exception {

    http.csrf(AbstractHttpConfigurer::disable)
        .cors(AbstractHttpConfigurer::disable)
        .formLogin(AbstractHttpConfigurer::disable);

    http.sessionManagement(sessionConfigurer -> sessionConfigurer
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

    configureApiEndPoints(http);

    http.oauth2Login(oauth2LoginConfigurer -> oauth2LoginConfigurer
        .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig
            .userService(oAuth2UserService))
        .successHandler(handlerConfig.getOAuth2LoginSuccessHandler()));

    http.logout(logoutConfigurer -> logoutConfigurer
        .logoutUrl("/api/accounts/signout")
        .addLogoutHandler(handlerConfig.getOAuth2LogoutHandler())
        .logoutSuccessHandler(handlerConfig.getOAuth2LogoutSuccessHandler()));

    http.addFilterBefore(filterConfig.getJwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(filterConfig.getExceptionHandleFilter(), JWTAuthenticationFilter.class);

    return http.build();
  }

  private void configureAdminEndpoints(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(registry -> {
          adminPermitAllEndPoints.forEach(
              endpoint -> registry.requestMatchers(endpoint.method(), endpoint.pattern()).permitAll());
          registry.anyRequest().hasRole(Role.ADMIN.name());
        }
    );
  }

  private void configureApiEndPoints(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(registry -> {
      apiPermitAllEndPoints.forEach(
          endPoint -> registry.requestMatchers(endPoint.method(), endPoint.pattern()).permitAll());
      registry.anyRequest().authenticated();
    });
  }

}