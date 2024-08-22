package com.hiddenartist.backend.domain.artwork.controller.response;

import com.hiddenartist.backend.domain.artist.persistence.Artist;
import com.hiddenartist.backend.domain.artwork.persistence.Artwork;
import com.hiddenartist.backend.domain.genre.persistence.Genre;
import com.hiddenartist.backend.global.type.EntityToken;
import java.util.List;

public record ArtworkGetDetailResponse(
    String name,

    String image,

    List<String> genres,

    Double width,

    Double height,

    Double depth,

    List<ArtistInfo> artists,

    String medium,

    Integer productionYear,

    String description
) {

  public static ArtworkGetDetailResponse create(Artwork artwork, List<Artist> artists, List<Genre> genres) {
    List<String> genreNames = genres.stream().map(Genre::getName).toList();
    List<ArtistInfo> artistInfos = artists.stream().map(ArtistInfo::create).toList();
    return new ArtworkGetDetailResponse(
        artwork.getName(),
        artwork.getImage(),
        genreNames,
        artwork.getWidth(),
        artwork.getHeight(),
        artwork.getDepth(),
        artistInfos,
        artwork.getArtworkMedium().getTypeName(),
        artwork.getProductionYear().getYear(),
        artwork.getDescription()
    );
  }

  public record ArtistInfo(
      String name,
      String token
  ) {

    public static ArtistInfo create(Artist artist) {
      return new ArtistInfo(artist.getName(), EntityToken.ARTIST.extractToken(artist.getToken()));
    }
  }

}