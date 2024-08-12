package com.pop.backend.domain.account.persistence.repository;

import com.pop.backend.domain.account.persistence.Account;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long>, QAccountRepository {

  Optional<Account> findByEmail(String email);

}