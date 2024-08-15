package com.pop.backend.domain.artist.persistence.repository;

import static com.pop.backend.domain.account.persistence.QAccount.account;
import static com.pop.backend.domain.artist.persistence.QArtist.artist;
import static com.pop.backend.domain.artist.persistence.QArtistContact.artistContact;
import static com.pop.backend.domain.artist.persistence.QFollowArtist.followArtist;
import static com.pop.backend.domain.genre.persistence.QArtistGenre.artistGenre;
import static com.pop.backend.domain.genre.persistence.QGenre.genre;

import com.pop.backend.domain.account.persistence.Account;
import com.pop.backend.domain.artist.controller.response.ArtistGetDetailResponse;
import com.pop.backend.domain.artist.controller.response.ArtistSimpleResponse;
import com.pop.backend.domain.artist.persistence.Artist;
import com.pop.backend.domain.artist.persistence.ArtistContact;
import com.pop.backend.domain.genre.persistence.ArtistGenre;
import com.pop.backend.domain.genre.persistence.Genre;
import com.pop.backend.global.exception.type.EntityException;
import com.pop.backend.global.exception.type.ServiceErrorCode;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.SimplePath;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
@RequiredArgsConstructor
public class CustomArtistRepositoryImpl implements CustomArtistRepository {

  private final JPAQueryFactory queryFactory;

  @Override
  public List<Artist> findFollowArtistListByAccountEmail(String email) {
    Account findAccount = queryFactory.selectFrom(account).where(emailEq(email)).fetchOne();
    return queryFactory.select(artist)
                       .from(followArtist)
                       .join(followArtist.artist, artist)
                       .where(followArtist.account.eq(findAccount))
                       .fetch();
  }

  @Override
  public Page<ArtistSimpleResponse> findAllArtists(Pageable pageable) {
    List<ArtistSimpleResponse> artists = queryFactory.select(Projections.constructor(ArtistSimpleResponse.class,
                                                         artist.name,
                                                         artist.profileImage,
                                                         artist.summary,
                                                         artist.token))
                                                     .from(artist)
                                                     .offset(pageable.getOffset())
                                                     .limit(pageable.getPageSize())
                                                     .orderBy(createOrderSpecifier(pageable.getSort()))
                                                     .fetch();

    JPAQuery<Long> countQuery = queryFactory.select(artist.count()).from(artist);
    return PageableExecutionUtils.getPage(artists, pageable, countQuery::fetchOne);
  }

  @Override
  public ArtistGetDetailResponse findArtistDetailByToken(String token) {
    Artist findArtist = Optional.ofNullable(queryFactory.selectFrom(artist).where(tokenEq(token)).fetchOne())
                                .orElseThrow(() -> new EntityException(ServiceErrorCode.ARTIST_NOT_FOUND));

    List<ArtistContact> findArtistContacts = queryFactory.selectFrom(artistContact)
                                                         .where(artistContact.artist.eq(findArtist))
                                                         .fetch();

    List<ArtistGenre> artistGenres = queryFactory.selectFrom(artistGenre)
                                                 .leftJoin(artistGenre.genre, genre)
                                                 .fetchJoin()
                                                 .where(artistGenre.artist.eq(findArtist))
                                                 .fetch();

    List<Genre> genres = artistGenres.stream().map(ArtistGenre::getGenre).toList();
    return ArtistGetDetailResponse.create(findArtist, findArtistContacts, genres);
  }

  @Override
  public List<Artist> findPopularArtists() {
    return queryFactory.select(followArtist.artist).from(followArtist)
                       .groupBy(followArtist.artist)
                       .orderBy(followArtist.artist.count().desc(), followArtist.artist.name.asc())
                       .limit(3)
                       .fetch();
  }

  @Override
  public List<Artist> findNewArtists() {
    return queryFactory.selectFrom(artist)
                       .orderBy(artist.create_date.desc(), artist.name.asc())
                       .limit(3)
                       .fetch();
  }

  private BooleanExpression emailEq(String email) {
    return StringUtils.hasText(email) ? account.email.eq(email) : Expressions.TRUE;
  }

  private BooleanExpression tokenEq(String token) {
    return StringUtils.hasText(token) ? artist.token.eq(token) : Expressions.TRUE;
  }

  private OrderSpecifier<?>[] createOrderSpecifier(Sort sort) {
    return sort.stream().map(order -> {
      Order direction = order.isAscending() ? Order.ASC : Order.DESC; // 정렬 방향
      SimplePath<Artist> path = Expressions.path(Artist.class, artist, order.getProperty());
      return new OrderSpecifier(direction, path);
    }).toArray(OrderSpecifier[]::new);
  }

}