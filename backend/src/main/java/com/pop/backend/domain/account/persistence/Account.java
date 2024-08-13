package com.pop.backend.domain.account.persistence;

import com.pop.backend.domain.account.persistence.type.ProviderType;
import com.pop.backend.domain.account.persistence.type.Role;
import com.pop.backend.global.type.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account extends BaseEntity {

  private String email;

  private String password;

  private String nickname;

  private String profileImage;

  @Default
  @Enumerated(value = EnumType.STRING)
  private ProviderType providerType = ProviderType.LOCAL;

  private Long providerId;

  @Default
  @Enumerated(value = EnumType.STRING)
  private Role role = Role.USER;

  private Account(String email, String password, String nickname, String profileImage, ProviderType providerType,
      Long providerId, Role role) {
    this.email = email;
    this.password = password;
    this.nickname = nickname;
    this.profileImage = profileImage;
    this.providerType = providerType;
    this.providerId = providerId;
    this.role = role;
  }

  public String getRoleKey() {
    return role.getKey();
  }

}