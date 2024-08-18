package com.pop.backend.domain.artist.controller.response;

import com.pop.backend.domain.artwork.persistence.Artwork;
import com.pop.backend.global.type.SimpleArtworkResponse;
import java.util.List;

public record ArtistGetAllArtworkResponse(
    List<SimpleArtworkResponse> artworks
) {

  public static ArtistGetAllArtworkResponse create(List<Artwork> artworks) {
    List<SimpleArtworkResponse> simpleArtworkResponses = artworks.stream().map(SimpleArtworkResponse::convert).toList();
    return new ArtistGetAllArtworkResponse(simpleArtworkResponses);
  }

}