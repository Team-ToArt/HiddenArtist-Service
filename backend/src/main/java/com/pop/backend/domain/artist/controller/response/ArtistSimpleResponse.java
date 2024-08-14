package com.pop.backend.domain.artist.controller.response;

import com.pop.backend.domain.artist.persistence.Artist;

public record ArtistSimpleResponse(
    String name,
    String image,
    String summary,
    String token
) {

  public static ArtistSimpleResponse convert(Artist artist) {
    return new ArtistSimpleResponse(artist.getName(), artist.getProfileImage(), artist.getSummary(), artist.getToken());
  }
}