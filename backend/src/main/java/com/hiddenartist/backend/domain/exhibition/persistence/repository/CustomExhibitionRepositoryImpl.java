package com.hiddenartist.backend.domain.exhibition.persistence.repository;

import static com.hiddenartist.backend.domain.exhibition.persistence.QExhibition.exhibition;
import static com.hiddenartist.backend.domain.exhibition.persistence.QExhibitionLocation.exhibitionLocation;
import static com.hiddenartist.backend.domain.exhibition.persistence.QExhibitionManager.exhibitionManager;

import com.hiddenartist.backend.domain.exhibition.controller.response.ExhibitionSimpleResponse;
import com.hiddenartist.backend.domain.exhibition.persistence.Exhibition;
import com.hiddenartist.backend.global.utils.QueryDslUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CustomExhibitionRepositoryImpl implements CustomExhibitionRepository {

  private final JPAQueryFactory queryFactory;

  @Override
  public Page<ExhibitionSimpleResponse> findAllExhibitions(Pageable pageable) {
    List<ExhibitionSimpleResponse> result = queryFactory.select(
                                                            Projections.constructor(ExhibitionSimpleResponse.class,
                                                                exhibition.name,
                                                                exhibition.token,
                                                                exhibition.image,
                                                                exhibition.startDate,
                                                                exhibition.endDate))
                                                        .from(exhibition)
                                                        .offset(pageable.getOffset())
                                                        .limit(pageable.getPageSize())
                                                        .orderBy(
                                                            QueryDslUtils.createOrderSpecifier(pageable.getSort())
                                                        )
                                                        .fetch();
    JPAQuery<Long> countQuery = queryFactory.select(exhibition.count()).from(exhibition);
    return PageableExecutionUtils.getPage(result, pageable, countQuery::fetchOne);
  }

  @Override
  public Optional<Exhibition> findExhibitionByToken(String token) {
    return Optional.ofNullable(queryFactory.selectFrom(exhibition)
                                           .leftJoin(exhibition.location, exhibitionLocation)
                                           .fetchJoin()
                                           .leftJoin(exhibition.manager, exhibitionManager)
                                           .fetchJoin()
                                           .where(exhibition.token.eq(token))
                                           .fetchOne());
  }

  @Override
  public List<Exhibition> findCurrentExhibitions() {
    return List.of();
  }

  @Override
  public List<Exhibition> findUpcomingExhibitions() {
    return List.of();
  }

  @Override
  public Page<Exhibition> findPastExhibitions() {
    return null;
  }
}