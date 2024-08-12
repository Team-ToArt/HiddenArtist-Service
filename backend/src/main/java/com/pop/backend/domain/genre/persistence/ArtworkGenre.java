package com.pop.backend.domain.genre.persistence;

import com.pop.backend.domain.artwork.persistence.Artwork;
import com.pop.backend.global.type.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArtworkGenre extends BaseEntity {

  @ManyToOne
  private Artwork artwork;

  @ManyToOne
  private Genre genre;

  private ArtworkGenre(Artwork artwork, Genre genre) {
    this.artwork = artwork;
    this.genre = genre;
  }

}