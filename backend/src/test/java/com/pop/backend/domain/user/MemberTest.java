package com.pop.backend.domain.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import com.pop.backend.domain.member.persistence.Member;
import com.pop.backend.domain.member.persistence.type.ProviderType;
import com.pop.backend.domain.member.persistence.type.Role;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberTest {

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
    Member member = Member.builder()
                          .email(userEmail)
                          .password(userPassword)
                          .nickname(userNickname)
                          .build();
    Member admin = Member.builder()
                         .email(adminEmail)
                         .password(adminPassword)
                         .nickname(adminNickname)
                         .providerType(adminType)
                         .role(adminRole)
                         .build();
    List<Member> memberList = List.of(member, admin);

    //then
    assertThat(memberList)
        .extracting("providerType", "role")
        .containsExactly(
            tuple(defaultType, defaultRole),
            tuple(adminType, adminRole)
        );
  }
}