package com.hiddenartist.backend.domain.artist.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
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

  }
}