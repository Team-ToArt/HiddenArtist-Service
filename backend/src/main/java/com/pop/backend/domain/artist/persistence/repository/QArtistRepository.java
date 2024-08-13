package com.pop.backend.domain.artist.persistence.repository;

import com.pop.backend.domain.artist.persistence.Artist;
import java.util.List;

public interface QArtistRepository {

  List<Artist> findFollowArtistListByAccountEmail(String email);

}