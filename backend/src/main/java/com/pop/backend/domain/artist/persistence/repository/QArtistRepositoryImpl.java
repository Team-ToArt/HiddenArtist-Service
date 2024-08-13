package com.pop.backend.domain.artist.persistence.repository;

import static com.pop.backend.domain.account.persistence.QAccount.account;
import static com.pop.backend.domain.artist.persistence.QArtist.artist;
import static com.pop.backend.domain.artist.persistence.QFollowArtist.followArtist;

import com.pop.backend.domain.account.persistence.Account;
import com.pop.backend.domain.artist.persistence.Artist;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
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

}