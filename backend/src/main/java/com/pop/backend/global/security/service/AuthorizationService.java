package com.pop.backend.global.security.service;

import com.pop.backend.domain.user.entity.User;
import com.pop.backend.domain.user.repository.UserRepository;
import com.pop.backend.global.security.auth.OAuth2UserAttributes;
import com.pop.backend.global.utils.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthorizationService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  public User validateUser(OAuth2UserAttributes oAuth2UserAttributes) {
    return userRepository.findByEmail(oAuth2UserAttributes.email()).orElseGet(() -> save(oAuth2UserAttributes));
  }

  private User save(OAuth2UserAttributes oAuth2UserAttributes) {
    String encodedPassword = passwordEncoder.encodedOAuth2Password(
        oAuth2UserAttributes.providerType(), oAuth2UserAttributes.email());
    User user = oAuth2UserAttributes.toUser(encodedPassword);
    return userRepository.save(user);
  }
}