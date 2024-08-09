package com.pop.backend.domain.user.repository;

import com.pop.backend.global.type.Role;

public interface QUserRepository {

  boolean existsAdminByEmailAndRole(String email, Role role);

}