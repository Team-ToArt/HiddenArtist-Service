package com.hiddenartist.backend.global.utils;

import static com.hiddenartist.backend.domain.artist.persistence.QArtist.artist;

import com.hiddenartist.backend.domain.artist.persistence.Artist;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.SimplePath;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QueryDslUtils {

  public static OrderSpecifier<?>[] createOrderSpecifier(Sort sort) {
    return sort.stream().map(order -> {
      Order direction = order.isAscending() ? Order.ASC : Order.DESC; // 정렬 방향
      SimplePath<Artist> path = Expressions.path(Artist.class, artist, order.getProperty());
      return new OrderSpecifier(direction, path);
    }).toArray(OrderSpecifier[]::new);
  }
}