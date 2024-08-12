package com.pop.backend.domain.member.persistence.repository;


import static com.pop.backend.domain.member.persistence.QMember.member;

import com.pop.backend.domain.member.persistence.type.Role;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class QMemberRepositoryImpl implements QMemberRepository {

  private final JPAQueryFactory queryFactory;

  @Override
  public boolean existsAdminByEmailAndRole(String email, Role role) {
    return Objects.nonNull(queryFactory.selectFrom(member)
                                       .where(
                                           member.email.eq(email).and(member.role.eq(role))
                                       )
                                       .fetchOne());
  }

}