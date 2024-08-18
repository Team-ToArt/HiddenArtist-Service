package com.hiddenartist.backend.domain.account.persistence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import com.hiddenartist.backend.domain.account.persistence.type.ProviderType;
import com.hiddenartist.backend.domain.account.persistence.type.Role;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AccountTest {

  @Test
  @DisplayName("User Builder Test")
  void userBuilder() {
    //given
    String userEmail = "test@test.com";
    String userPassword = "12345";
    String userNickname = "blanc";
    ProviderType defaultType = ProviderType.LOCAL;
    Role defaultRole = Role.USER;

    String adminEmail = "admin@test.com";
    String adminPassword = "admin";
    String adminNickname = "admin";
    ProviderType adminType = ProviderType.LOCAL;
    Role adminRole = Role.ADMIN;

    //when
    Account account = Account.builder()
                             .email(userEmail)
                             .password(userPassword)
                             .nickname(userNickname)
                             .build();
    Account admin = Account.builder()
                           .email(adminEmail)
                           .password(adminPassword)
                           .nickname(adminNickname)
                           .providerType(adminType)
                           .role(adminRole)
                           .build();
    List<Account> accountList = List.of(account, admin);

    //then
    assertThat(accountList)
        .extracting("providerType", "role")
        .containsExactly(
            tuple(defaultType, defaultRole),
            tuple(adminType, adminRole)
        );
  }
}