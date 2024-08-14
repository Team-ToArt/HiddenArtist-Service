package com.pop.backend.domain.artist.service;

import com.pop.backend.domain.artist.controller.response.ArtistGetListResponse;
import com.pop.backend.domain.artist.controller.response.ArtistSimpleResponse;
import com.pop.backend.domain.artist.persistence.repository.ArtistRepository;
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

}