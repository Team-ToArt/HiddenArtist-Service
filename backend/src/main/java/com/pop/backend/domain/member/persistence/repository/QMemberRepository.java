package com.pop.backend.domain.member.persistence.repository;

import com.pop.backend.domain.member.persistence.type.Role;

public interface QMemberRepository {

  boolean existsAdminByEmailAndRole(String email, Role role);

}