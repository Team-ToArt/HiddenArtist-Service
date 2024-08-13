package com.pop.backend.domain.account.controller.response;

import com.pop.backend.domain.artist.persistence.Artist;
import com.pop.backend.global.type.ArtistResponse;
import java.util.List;

public record FollowArtistGetListResponse(
    List<ArtistResponse> artists
) {

  public static FollowArtistGetListResponse convert(List<Artist> artists) {
    List<ArtistResponse> artistResponses = artists.stream().map(ArtistResponse::convert).toList();
    return new FollowArtistGetListResponse(artistResponses);
  }
}