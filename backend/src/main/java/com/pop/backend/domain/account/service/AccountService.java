package com.pop.backend.domain.account.service;

import com.pop.backend.domain.account.persistence.Account;
import com.pop.backend.domain.account.persistence.repository.AccountRepository;
import com.pop.backend.global.exception.type.EntityException;
import com.pop.backend.global.exception.type.SecurityException;
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

  @Transactional
  public void withDrawAccount(String email, String refreshToken) {
    Account account = findAccountByEmail(email);
    validateAccountStatus(account);
    unlinkAccount(account);
    tokenService.removeRefreshToken(refreshToken);
    account.updateDeleteDate();
    accountRepository.save(account);
  }
  
  private Account findAccountByEmail(String email) {
    return accountRepository.findByEmail(email)
                            .orElseThrow(() -> new EntityException(ServiceErrorCode.USER_NOT_FOUND));
  }

  private void validateAccountStatus(Account account) {
    if (account.isDeleted()) {
      throw new EntityException(ServiceErrorCode.ACCOUNT_ALREADY_DELETED);
    }
  }


  private void unlinkAccount(Account account) {
    try {
      if (!unlinkManager.unlink(account.getProviderType(), String.valueOf(account.getProviderId()))) {
        throw new SecurityException(ServiceErrorCode.UNLINK_FAIL);
      }
    } catch (Exception e) {
      throw new SecurityException(ServiceErrorCode.UNLINK_FAIL);
    }
  }

}