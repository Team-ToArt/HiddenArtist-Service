package com.pop.backend.domain.account.persistence.repository;


import static com.pop.backend.domain.account.persistence.QAccount.account;

import com.pop.backend.domain.account.persistence.type.Role;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class QAccountRepositoryImpl implements QAccountRepository {

  private final JPAQueryFactory queryFactory;

  @Override
  public boolean existsAdminByEmailAndRole(String email, Role role) {
    return Objects.nonNull(queryFactory.selectFrom(account)
                                       .where(
                                           account.email.eq(email).and(account.role.eq(role))
                                       )
                                       .fetchOne());
  }

}