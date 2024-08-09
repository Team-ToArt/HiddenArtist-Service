package com.pop.backend.domain.user.repository;

import static com.pop.backend.domain.user.entity.QUser.user;

import com.pop.backend.global.type.Role;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class QUserRepositoryImpl implements QUserRepository {

  private final JPAQueryFactory queryFactory;

  @Override
  public boolean existsAdminByEmailAndRole(String email, Role role) {
    return Objects.nonNull(queryFactory.selectFrom(user)
                                       .where(
                                           user.email.eq(email).and(user.role.eq(role))
                                       )
                                       .fetchOne());
  }

}