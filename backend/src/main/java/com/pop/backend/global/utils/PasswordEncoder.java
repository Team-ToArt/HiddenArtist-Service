package com.pop.backend.global.utils;

import com.pop.backend.global.type.ProviderType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordEncoder {

  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  public String encoded(String password) {
    return bCryptPasswordEncoder.encode(password);
  }

  public String encodedOAuth2Password(ProviderType providerType, String email) {
    return bCryptPasswordEncoder.encode(providerType.name() + "_" + email);
  }

}