package com.hiddenartist.backend.domain.artwork.controller.response;

import com.hiddenartist.backend.domain.artwork.persistence.Artwork;
import com.hiddenartist.backend.global.type.SimpleArtworkResponse;
import java.util.List;

public record ArtworkGetRecommendResponse(
    List<SimpleArtworkResponse> artworks
) {

  public static ArtworkGetRecommendResponse create(List<Artwork> artworks) {
    List<SimpleArtworkResponse> simpleArtworkResponses = artworks.stream().map(SimpleArtworkResponse::convert).toList();
    return new ArtworkGetRecommendResponse(simpleArtworkResponses);
  }

}