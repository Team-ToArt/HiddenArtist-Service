package com.pop.backend.global.security.service;

import com.pop.backend.domain.account.persistence.Account;
import com.pop.backend.domain.account.persistence.repository.AccountRepository;
import com.pop.backend.global.exception.type.EntityException;
import com.pop.backend.global.exception.type.ServiceErrorCode;
import com.pop.backend.global.security.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final AccountRepository memberRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Account account = memberRepository.findByEmail(username)
                                      .orElseThrow(() -> new EntityException(ServiceErrorCode.USER_NOT_FOUND));
    return PrincipalDetails.create(account);
  }

}