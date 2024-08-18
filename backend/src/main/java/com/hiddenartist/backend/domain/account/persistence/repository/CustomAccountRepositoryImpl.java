package com.hiddenartist.backend.domain.account.persistence.repository;


import static com.hiddenartist.backend.domain.account.persistence.QAccount.account;
import static com.hiddenartist.backend.domain.artist.persistence.QArtist.artist;
import static com.hiddenartist.backend.domain.artist.persistence.QFollowArtist.followArtist;

import com.hiddenartist.backend.domain.account.persistence.Account;
import com.hiddenartist.backend.domain.account.persistence.type.Role;
import com.hiddenartist.backend.domain.artist.persistence.Artist;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CustomAccountRepositoryImpl implements CustomAccountRepository {

  private final JPAQueryFactory queryFactory;

  @Override
  public boolean existsAdminByEmailAndRole(String email, Role role) {
    return Objects.nonNull(queryFactory.selectFrom(account)
                                       .where(
                                           account.email.eq(email).and(account.role.eq(role))
                                       ).fetchOne());
  }

  @Override
  public void removeFollowArtists(String email, List<String> tokens) {
    List<Artist> artists = queryFactory.selectFrom(artist)
                                       .where(artist.token.in(tokens))
                                       .fetch();
    if (artists.isEmpty()) {
      return;
    }
    queryFactory.delete(followArtist)
                .where(
                    followArtist.artist.in(artists)
                                       .and(followArtist.account.email.eq(email))
                ).execute();
  }

  @Override
  public List<Artist> findFollowArtistListByEmail(String email) {
    Account findAccount = queryFactory.selectFrom(account).where(account.email.eq(email)).fetchOne();
    return queryFactory.select(artist)
                       .from(followArtist)
                       .join(followArtist.artist, artist)
                       .where(followArtist.account.eq(findAccount))
                       .fetch();
  }

}