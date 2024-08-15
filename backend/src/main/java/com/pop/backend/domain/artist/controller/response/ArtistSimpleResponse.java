package com.pop.backend.domain.artist.controller.response;

import com.pop.backend.domain.artist.persistence.Artist;
import com.pop.backend.global.type.EntityToken;

public record ArtistSimpleResponse(
    String name,
    String image,
    String summary,
    String token
) {

  public ArtistSimpleResponse(String name, String image, String summary, String token) {
    this.name = name;
    this.image = image;
    this.summary = summary;
    this.token = EntityToken.ARTIST.extractToken(token);
  }

  public static ArtistSimpleResponse convert(Artist artist) {
    String extractToken = EntityToken.ARTIST.extractToken(artist.getToken());
    return new ArtistSimpleResponse(artist.getName(), artist.getProfileImage(), artist.getSummary(), extractToken);
  }
}