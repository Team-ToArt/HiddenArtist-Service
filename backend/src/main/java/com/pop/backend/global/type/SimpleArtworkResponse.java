package com.pop.backend.global.type;

import com.pop.backend.domain.artwork.persistence.Artwork;

public record SimpleArtworkResponse(
    String name,
    String image,
    String token
) {

  public SimpleArtworkResponse(String name, String image, String token) {
    this.name = name;
    this.image = image;
    this.token = EntityToken.ARTWORK.extractToken(token);
  }

  public static SimpleArtworkResponse convert(Artwork artwork) {
    return new SimpleArtworkResponse(artwork.getName(), artwork.getImage(), artwork.getToken());
  }
}