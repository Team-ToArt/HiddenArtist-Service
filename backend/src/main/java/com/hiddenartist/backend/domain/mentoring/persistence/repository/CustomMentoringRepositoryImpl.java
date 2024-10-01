package com.hiddenartist.backend.domain.mentoring.persistence.repository;

import static com.hiddenartist.backend.domain.account.persistence.QAccount.account;
import static com.hiddenartist.backend.domain.mentoring.persistence.QMentor.mentor;
import static com.hiddenartist.backend.domain.mentoring.persistence.QMentoring.mentoring;

import com.hiddenartist.backend.domain.mentoring.persistence.Mentoring;
import com.hiddenartist.backend.domain.mentoring.persistence.type.MentoringStatus;
import com.hiddenartist.backend.global.utils.QueryDslUtils;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CustomMentoringRepositoryImpl implements CustomMentoringRepository {

  private final JPAQueryFactory queryFactory;

  @Override
  public Page<Mentoring> findAllMentorings(Pageable pageable) {
    List<Mentoring> result = queryFactory.selectFrom(mentoring)
                                         .leftJoin(mentoring.mentor, mentor)
                                         .fetchJoin()
                                         .leftJoin(mentor.account, account)
                                         .fetchJoin()
                                         .where(mentoring.mentoringStatus.eq(MentoringStatus.OPEN)
                                                                         .and(mentoring.deleteDate.isNull()))
                                         .offset(pageable.getOffset())
                                         .limit(pageable.getPageSize())
                                         .orderBy(QueryDslUtils.createOrderSpecifier(pageable.getSort()))
                                         .fetch();
    JPAQuery<Long> countQuery = queryFactory.select(mentoring.count()).from(mentoring);
    return PageableExecutionUtils.getPage(result, pageable, countQuery::fetchOne);
  }

}