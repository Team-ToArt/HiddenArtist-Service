package com.hiddenartist.backend.domain.account.controller.response;

import com.hiddenartist.backend.domain.artist.persistence.Artist;
import com.hiddenartist.backend.global.type.SimpleArtistResponse;
import java.util.List;

public record FollowArtistGetListResponse(
    List<SimpleArtistResponse> artists
) {

  public static FollowArtistGetListResponse convert(List<Artist> artists) {
    List<SimpleArtistResponse> simpleArtistResponses = artists.stream().map(SimpleArtistResponse::convert).toList();
    return new FollowArtistGetListResponse(simpleArtistResponses);
  }
}