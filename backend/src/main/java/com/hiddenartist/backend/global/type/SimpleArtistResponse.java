package com.hiddenartist.backend.global.type;

import com.hiddenartist.backend.domain.artist.persistence.Artist;

public record SimpleArtistResponse(
    String name,
    String image,
    String summary,
    String token
) {

  public SimpleArtistResponse(String name, String image, String summary, String token) {
    this.name = name;
    this.image = image;
    this.summary = summary;
    this.token = EntityToken.ARTIST.extractToken(token);
  }

  public static SimpleArtistResponse convert(Artist artist) {
    return new SimpleArtistResponse(artist.getName(), artist.getProfileImage(), artist.getSummary(), artist.getToken());
  }
}