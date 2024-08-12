package com.pop.backend.domain.artist.persistence;

import com.pop.backend.global.type.BaseEntity;
import jakarta.persistence.Entity;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Artist extends BaseEntity {

  private String name;

  private LocalDate birth;

  private String profileImage;

  private String description;

  private Artist(String name, LocalDate birth, String profileImage, String description) {
    this.name = name;
    this.birth = birth;
    this.profileImage = profileImage;
    this.description = description;
  }

}