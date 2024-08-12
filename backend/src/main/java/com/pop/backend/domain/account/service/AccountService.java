package com.pop.backend.domain.account.service;

import com.pop.backend.domain.account.persistence.Account;
import com.pop.backend.domain.account.persistence.repository.AccountRepository;
import com.pop.backend.global.exception.type.EntityException;
import com.pop.backend.global.exception.type.ServiceErrorCode;
import com.pop.backend.global.jwt.TokenService;
import com.pop.backend.global.security.service.OAuth2UnlinkManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountService {

  private final AccountRepository accountRepository;
  private final TokenService tokenService;
  private final OAuth2UnlinkManager unlinkManager;

  // 내일 좀 더 구체적으로 로직을 구성할 것
  @Transactional
  public void withDrawAccount(String email, String refreshToken) {
    // 0. User Entity 조회
    Account account = accountRepository.findByEmail(email)
                                       .orElseThrow(() -> new EntityException(ServiceErrorCode.USER_NOT_FOUND));
    // 1. 연결끊기
    unlinkManager.unlink(account.getProviderType(), String.valueOf(account.getProviderId()));
    // 2. redis, httpsession 토큰 삭제
    tokenService.removeRefreshToken(refreshToken);
    // 3. Repository 에서 삭제 -> Soft Delete
    account.updateDeleteDate();
    accountRepository.save(account);
  }

}