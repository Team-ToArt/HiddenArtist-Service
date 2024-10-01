package com.hiddenartist.backend.domain.mentoring.persistence.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CustomMentoringRepositoryImpl implements CustomMentoringRepository {

  private final JPAQueryFactory queryFactory;

}