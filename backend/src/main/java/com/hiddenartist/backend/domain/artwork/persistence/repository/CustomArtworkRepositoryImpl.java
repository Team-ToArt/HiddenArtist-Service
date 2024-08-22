package com.hiddenartist.backend.domain.artwork.persistence.repository;

import static com.hiddenartist.backend.domain.artist.persistence.QArtist.artist;
import static com.hiddenartist.backend.domain.artist.persistence.QArtistArtwork.artistArtwork;
import static com.hiddenartist.backend.domain.artwork.persistence.QArtwork.artwork;
import static com.hiddenartist.backend.domain.artwork.persistence.QArtworkMedium.artworkMedium;
import static com.hiddenartist.backend.domain.genre.persistence.QArtworkGenre.artworkGenre;
import static com.hiddenartist.backend.domain.genre.persistence.QGenre.genre;

import com.hiddenartist.backend.domain.artist.persistence.Artist;
import com.hiddenartist.backend.domain.artwork.controller.response.ArtworkGetDetailResponse;
import com.hiddenartist.backend.domain.artwork.persistence.Artwork;
import com.hiddenartist.backend.domain.genre.persistence.Genre;
import com.hiddenartist.backend.global.exception.type.EntityException;
import com.hiddenartist.backend.global.exception.type.ServiceErrorCode;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CustomArtworkRepositoryImpl implements CustomArtworkRepository {

  private final JPAQueryFactory queryFactory;

  @Override
  public ArtworkGetDetailResponse findArtworkDetailByToken(String token) {
    // 토큰을 사용해서 Artwork 조회 -> ArtworkMedium fetch join
    Artwork findArtwork = Optional.ofNullable(queryFactory.selectFrom(artwork)
                                                          .leftJoin(artwork.artworkMedium, artworkMedium)
                                                          .fetchJoin()
                                                          .where(artwork.token.eq(token))
                                                          .fetchOne())
                                  .orElseThrow(() -> new EntityException(ServiceErrorCode.ARTWORK_NOT_FOUND));

    // Artwork를 통해 Artist 조회 -> 이름, 토큰 추출
    List<Artist> artists = queryFactory.select(artist)
                                       .from(artistArtwork)
                                       .join(artistArtwork.artist, artist)
                                       .fetchJoin()
                                       .where(artistArtwork.artwork.eq(findArtwork))
                                       .orderBy(artist.name.asc())
                                       .fetch();
    // Artwork를 통해 ArtworkGenre 조회 -> List<Genre> 추출
    List<Genre> genres = queryFactory.select(genre)
                                     .from(artworkGenre)
                                     .join(artworkGenre.genre, genre)
                                     .fetchJoin()
                                     .where(artworkGenre.artwork.eq(findArtwork))
                                     .orderBy(genre.name.asc())
                                     .fetch();
    return ArtworkGetDetailResponse.create(findArtwork, artists, genres);
  }
}