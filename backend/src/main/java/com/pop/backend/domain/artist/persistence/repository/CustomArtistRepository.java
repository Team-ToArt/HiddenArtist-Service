package com.pop.backend.domain.artist.persistence.repository;

import com.pop.backend.domain.artist.persistence.Artist;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomArtistRepository {

  List<Artist> findFollowArtistListByAccountEmail(String email);

  Page<Artist> findAllArtists(Pageable pageable);

}