package com.pop.backend.domain.artist.controller.response;

import com.pop.backend.domain.artist.persistence.Artist;
import com.pop.backend.global.type.EntityToken;

public record ArtistSimpleResponse(
    String name,
    String image,
    String summary,
    String token
) {

  public static ArtistSimpleResponse convert(Artist artist) {
    String extractToken = EntityToken.ARTIST.extractToken(artist.getToken());
    return new ArtistSimpleResponse(artist.getName(), artist.getProfileImage(), artist.getSummary(), extractToken);
  }
}