package com.pop.backend.domain.account.persistence.repository;

import com.pop.backend.domain.account.persistence.type.Role;
import java.util.List;

public interface QAccountRepository {

  boolean existsAdminByEmailAndRole(String email, Role role);

  void removeFollowArtists(String email, List<String> token);

}