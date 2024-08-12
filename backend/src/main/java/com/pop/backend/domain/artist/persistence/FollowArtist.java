package com.pop.backend.domain.artist.persistence;

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
public class FollowArtist extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  private Member member;
  @ManyToOne(fetch = FetchType.LAZY)
  private Artist artist;

  private FollowArtist(Member member, Artist artist) {
    this.member = member;
    this.artist = artist;
  }

}