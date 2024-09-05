package com.hiddenartist.backend.domain.genre.persistence.repository;

import com.hiddenartist.backend.domain.genre.persistence.Genre;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CustomGenreRepositoryImpl implements CustomGenreRepository {

  private final JPAQueryFactory queryFactory;

  @Override
  public List<Genre> findGenreByKeyword(String keyword) {
    return List.of();
  }
}