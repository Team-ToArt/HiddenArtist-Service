package com.hiddenartist.backend.domain.artist.controller.response;

import com.hiddenartist.backend.domain.artist.persistence.Artist;
import com.hiddenartist.backend.global.type.SimpleArtistResponse;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public record ArtistGetListResponse(
    Page<SimpleArtistResponse> artists
) {

  public static ArtistGetListResponse create(Page<Artist> artists) {
    List<SimpleArtistResponse> simpleResponses = artists.get().map(SimpleArtistResponse::convert).toList();
    Page<SimpleArtistResponse> artistSimpleResponses = new PageImpl<>(simpleResponses, artists.getPageable(),
        artists.getTotalPages());
    return new ArtistGetListResponse(artistSimpleResponses);
  }
}