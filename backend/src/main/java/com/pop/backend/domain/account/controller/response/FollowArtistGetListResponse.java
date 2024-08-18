package com.pop.backend.domain.account.controller.response;

import com.pop.backend.domain.artist.persistence.Artist;
import com.pop.backend.global.type.SimpleArtistResponse;
import java.util.List;

public record FollowArtistGetListResponse(
    List<SimpleArtistResponse> artists
) {

  public static FollowArtistGetListResponse convert(List<Artist> artists) {
    List<SimpleArtistResponse> simpleArtistResponses = artists.stream().map(SimpleArtistResponse::convert).toList();
    return new FollowArtistGetListResponse(simpleArtistResponses);
  }
}