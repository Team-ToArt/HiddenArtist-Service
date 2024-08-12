package com.pop.backend.global.security.auth;

import com.pop.backend.domain.member.persistence.Member;
import com.pop.backend.domain.member.persistence.type.ProviderType;
import java.io.Serializable;

public record SecurityUserInfo(
    String email,
    String nickname,
    String password,
    ProviderType providerType
) implements Serializable {

  public static SecurityUserInfo convert(Member member) {
    return new SecurityUserInfo(member.getEmail(), member.getNickname(), member.getPassword(),
        member.getProviderType());
  }

}