package com.hiddenartist.backend.domain.artist.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hiddenartist.backend.global.type.EntityToken;
import java.util.List;

public record ArtistGetSignatureArtworkResponse(
    List<ArtworkResponse> artworks
) {


  public record ArtworkResponse(
      String name,

      String image,

      String token,

      @JsonProperty("display_order")
      Byte displayOrder
  ) {

    public ArtworkResponse(String name, String image, String token, @JsonProperty("display_order") Byte displayOrder) {
      this.name = name;
      this.image = image;
      this.token = EntityToken.ARTWORK.extractToken(token);
      this.displayOrder = displayOrder;
    }

  }

}