package com.pop.backend.domain.artist.controller.response;

import com.pop.backend.domain.artist.persistence.Artist;
import com.pop.backend.global.type.SimpleArtistResponse;
import java.util.List;

public record ArtistGetThreeResponse(
    List<SimpleArtistResponse> artists
) {

  public static ArtistGetThreeResponse create(List<Artist> artists) {
    List<SimpleArtistResponse> simpleResponses = artists.stream().map(SimpleArtistResponse::convert).toList();
    return new ArtistGetThreeResponse(simpleResponses);
  }

}