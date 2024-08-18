package com.pop.backend.domain.artwork.persistence;

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
public class Artwork extends BaseEntity {

  private String name;

  private String image;

  private String description;

  private LocalDate productionYear;

  private String token;

  private Artwork(String name, String image, String description, LocalDate productionYear, String token) {
    this.name = name;
    this.image = image;
    this.description = description;
    this.productionYear = productionYear;
    this.token = token;
  }

}