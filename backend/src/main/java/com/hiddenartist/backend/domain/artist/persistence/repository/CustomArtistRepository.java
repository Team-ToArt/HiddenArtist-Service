package com.hiddenartist.backend.domain.artist.persistence.repository;

import com.hiddenartist.backend.domain.artist.controller.response.ArtistGetDetailResponse;
import com.hiddenartist.backend.domain.artist.controller.response.ArtistGetSignatureArtworkResponse.ArtworkResponse;
import com.hiddenartist.backend.domain.artist.persistence.Artist;
import com.hiddenartist.backend.domain.artwork.persistence.Artwork;
import com.hiddenartist.backend.global.type.SimpleArtistResponse;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomArtistRepository {

  List<Artist> findFollowArtistListByAccountEmail(String email);

  Page<SimpleArtistResponse> findAllArtists(Pageable pageable);

  ArtistGetDetailResponse findArtistDetailByToken(String token);

  List<Artist> findPopularArtists();

  List<Artist> findNewArtists();

  List<ArtworkResponse> findSignatureArtworkByToken(String token);

  List<Artwork> findAllArtworkByToken(String token);

}