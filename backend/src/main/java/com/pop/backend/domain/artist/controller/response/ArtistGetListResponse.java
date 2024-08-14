package com.pop.backend.domain.artist.controller.response;

import com.pop.backend.domain.artist.persistence.Artist;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public record ArtistGetListResponse(
    Page<ArtistSimpleResponse> artists
) {

  public static ArtistGetListResponse create(Page<Artist> artists) {
    List<ArtistSimpleResponse> simpleResponses = artists.get().map(ArtistSimpleResponse::convert).toList();
    Page<ArtistSimpleResponse> artistSimpleResponses = new PageImpl<>(simpleResponses, artists.getPageable(),
        artists.getTotalPages());
    return new ArtistGetListResponse(artistSimpleResponses);
  }
}