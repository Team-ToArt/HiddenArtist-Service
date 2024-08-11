package com.pop.backend.global.security.auth;

import com.pop.backend.domain.user.entity.User;
import com.pop.backend.global.type.ProviderType;
import java.io.Serializable;

public record SecurityUserInfo(
    String email,
    String nickname,
    String password,
    ProviderType providerType
) implements Serializable {

  public static SecurityUserInfo convert(User user) {
    return new SecurityUserInfo(user.getEmail(), user.getNickname(), user.getPassword(), user.getProviderType());
  }

}