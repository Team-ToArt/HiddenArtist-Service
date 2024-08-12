package com.pop.backend.global.security.service;

import com.pop.backend.domain.member.persistence.Member;
import com.pop.backend.domain.member.persistence.repository.MemberRepository;
import com.pop.backend.global.security.auth.OAuth2UserAttributes;
import com.pop.backend.global.utils.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthorizationService {

  private final MemberRepository memberRepository;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  public Member validateUser(OAuth2UserAttributes oAuth2UserAttributes) {
    return memberRepository.findByEmail(oAuth2UserAttributes.email()).orElseGet(() -> save(oAuth2UserAttributes));
  }

  private Member save(OAuth2UserAttributes oAuth2UserAttributes) {
    String encodedPassword = passwordEncoder.encodedOAuth2Password(
        oAuth2UserAttributes.providerType(), oAuth2UserAttributes.email());
    Member member = oAuth2UserAttributes.toMember(encodedPassword);
    return memberRepository.save(member);
  }
}