package com.pop.backend.domain.artist.service;

import com.pop.backend.domain.artist.controller.response.ArtistGetDetailResponse;
import com.pop.backend.domain.artist.controller.response.ArtistGetListResponse;
import com.pop.backend.domain.artist.controller.response.ArtistGetThreeResponse;
import com.pop.backend.domain.artist.controller.response.ArtistSimpleResponse;
import com.pop.backend.domain.artist.persistence.Artist;
import com.pop.backend.domain.artist.persistence.repository.ArtistRepository;
import com.pop.backend.global.type.EntityToken;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArtistService {

  private final ArtistRepository artistRepository;

  @Transactional(readOnly = true)
  public ArtistGetListResponse getAllArtists(Pageable pageable) {
    Pageable pageRequest = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort());
    Page<ArtistSimpleResponse> artists = artistRepository.findAllArtists(pageRequest);
    return new ArtistGetListResponse(artists);
  }

  @Transactional(readOnly = true)
  public ArtistGetDetailResponse getArtistDetail(String tokenValue) {
    String token = EntityToken.ARTIST.identifyToken(tokenValue);
    return artistRepository.findArtistDetailByToken(token);
  }

  @Transactional(readOnly = true)
  public ArtistGetThreeResponse getPopularArtists() {
    List<Artist> popularArtists = artistRepository.findPopularArtists();
    return ArtistGetThreeResponse.create(popularArtists);
  }

  @Transactional(readOnly = true)
  public ArtistGetThreeResponse getNewArtists() {
    List<Artist> newArtists = artistRepository.findNewArtists();
    return ArtistGetThreeResponse.create(newArtists);
  }

}