package com.pop.backend.domain.account.persistence.repository;

import com.pop.backend.domain.account.persistence.type.Role;

public interface QAccountRepository {

  boolean existsAdminByEmailAndRole(String email, Role role);

}