package com.hiddenartist.backend.domain.search.persistence.repository;

import static com.hiddenartist.backend.domain.artist.persistence.QArtist.artist;
import static com.hiddenartist.backend.domain.artwork.persistence.QArtwork.artwork;
import static com.hiddenartist.backend.domain.exhibition.persistence.QExhibition.exhibition;
import static com.hiddenartist.backend.domain.genre.persistence.QGenre.genre;

import com.hiddenartist.backend.domain.artist.persistence.Artist;
import com.hiddenartist.backend.domain.artwork.persistence.Artwork;
import com.hiddenartist.backend.domain.exhibition.persistence.Exhibition;
import com.hiddenartist.backend.domain.genre.persistence.Genre;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.dsl.BooleanTemplate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SearchRepositoryImpl implements SearchRepository {

  private static final String FULL_TEXT_SEARCH_SQL = "function('match',{0},{1})";
  private final JPAQueryFactory queryFactory;

  @Override
  public List<Artist> findArtistByKeyword(String keyword) {
    return findByKeyword(artist, artist.name, keyword);
  }

  @Override
  public List<Artwork> findArtworkByKeyword(String keyword) {
    return findByKeyword(artwork, artwork.name, keyword);
  }

  @Override
  public List<Genre> findGenreByKeyword(String keyword) {
    return findByKeyword(genre, genre.name, keyword);
  }

  @Override
  public List<Exhibition> findExhibitionByKeyword(String keyword) {
    return findByKeyword(exhibition, exhibition.name, keyword);
  }

  private <T> List<T> findByKeyword(EntityPath<T> entity, StringExpression column, String keyword) {
    return queryFactory.selectFrom(entity)
                       .where(fullTextSearch(column, keyword))
                       .limit(10)
                       .fetch();
  }

  private BooleanTemplate fullTextSearch(StringExpression idx, String keyword) {
    return Expressions.booleanTemplate(FULL_TEXT_SEARCH_SQL, idx, keyword);
  }

}