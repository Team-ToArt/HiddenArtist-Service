package com.pop.backend.domain.artwork.persistence;

import com.pop.backend.domain.artist.persistence.Artist;
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
public class FeaturedArtwork extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  private Artist artist;

  @ManyToOne(fetch = FetchType.LAZY)
  private Artwork artwork;

  private Byte displayOrder;

  private FeaturedArtwork(Artist artist, Artwork artwork, Byte displayOrder) {
    this.artist = artist;
    this.artwork = artwork;
    this.displayOrder = displayOrder;
  }

}