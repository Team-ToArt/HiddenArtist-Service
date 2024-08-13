package com.pop.backend.global.type;

import com.pop.backend.domain.artist.persistence.Artist;

public record ArtistResponse(
    String name,
    String image,
    String token
) {

  public static ArtistResponse convert(Artist artist) {
    return new ArtistResponse(artist.getName(), artist.getProfileImage(), artist.getToken());
  }

}