package com.pop.backend.domain.artist.persistence;

import com.pop.backend.domain.artist.persistence.type.ContactType;
import com.pop.backend.global.type.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class ArtistContact extends BaseEntity {

  @Enumerated(value = EnumType.STRING)
  private ContactType type;

  private String label;

  private String value;

  @ManyToOne(fetch = FetchType.LAZY)
  private Artist artist;

  private ArtistContact(ContactType type, String label, String value, Artist artist) {
    this.type = type;
    this.label = label;
    this.value = value;
    this.artist = artist;
  }

}