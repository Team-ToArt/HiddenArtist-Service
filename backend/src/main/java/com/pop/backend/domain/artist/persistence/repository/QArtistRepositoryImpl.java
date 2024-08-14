package com.pop.backend.domain.artist.persistence.repository;

import static com.pop.backend.domain.account.persistence.QAccount.account;
import static com.pop.backend.domain.artist.persistence.QArtist.artist;
import static com.pop.backend.domain.artist.persistence.QFollowArtist.followArtist;

import com.pop.backend.domain.account.persistence.Account;
import com.pop.backend.domain.artist.persistence.Artist;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class QArtistRepositoryImpl implements QArtistRepository {

  private final JPAQueryFactory queryFactory;

  @Override
  public List<Artist> findFollowArtistListByAccountEmail(String email) {
    Account findAccount = queryFactory.selectFrom(account).where(account.email.eq(email)).fetchOne();
    return queryFactory.select(artist)
                       .from(followArtist)
                       .join(followArtist.artist, artist)
                       .where(followArtist.account.eq(findAccount))
                       .fetch();
  }

  @Override
  public Page<Artist> findAllArtists(Pageable pageable) {
    List<Artist> artists = queryFactory.selectFrom(artist)
                                       .offset(pageable.getOffset())
                                       .limit(pageable.getPageSize())
                                       .orderBy(createOrderSpecifier(pageable.getSort()))
                                       .fetch();

    JPAQuery<Long> countQuery = queryFactory.select(artist.count()).from(artist);
    return PageableExecutionUtils.getPage(artists, pageable, countQuery::fetchOne);
  }

  private OrderSpecifier<?>[] createOrderSpecifier(Sort sort) {
    List<OrderSpecifier<?>> orders = new ArrayList<>();
    sort.forEach(order -> {
          PathBuilder<Artist> entityPath = new PathBuilder<>(Artist.class, "artist");
          Order direction = order.isAscending() ? Order.ASC : Order.DESC; // 정렬 방향
          String prop = order.getProperty(); // 정렬 변수
          OrderSpecifier<?> tOrderSpecifier = new OrderSpecifier(direction, entityPath.get(prop));
          orders.add(tOrderSpecifier);
        }
    );
    return orders.toArray(OrderSpecifier[]::new);
  }
}