package com.pop.backend.domain.account.controller.request;

import java.util.List;

public record AccountDeleteFollowArtistRequest(
    List<FollowArtistToken> artists
) {

  public record FollowArtistToken(
      String token
  ) {

  }

}