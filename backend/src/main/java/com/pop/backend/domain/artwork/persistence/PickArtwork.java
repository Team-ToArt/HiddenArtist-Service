package com.pop.backend.domain.artwork.persistence;

import com.pop.backend.domain.member.persistence.Member;
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
public class PickArtwork extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  private Member member;

  @ManyToOne(fetch = FetchType.LAZY)
  private Artwork artwork;

  private PickArtwork(Member member, Artwork artwork) {
    this.member = member;
    this.artwork = artwork;
  }

}