package com.pop.backend.domain.artist.persistence;

import com.pop.backend.domain.artwork.persistence.Artwork;
import com.pop.backend.global.type.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArtistArtwork extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  private Artist artist;

  @ManyToOne(fetch = FetchType.LAZY)
  private Artwork artwork;

  private ArtistArtwork(Artist artist, Artwork artwork) {
    this.artist = artist;
    this.artwork = artwork;
  }

}