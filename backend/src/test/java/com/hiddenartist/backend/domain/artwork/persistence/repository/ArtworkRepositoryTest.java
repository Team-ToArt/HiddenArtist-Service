package com.hiddenartist.backend.domain.artwork.persistence.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

import com.hiddenartist.backend.domain.artwork.controller.response.ArtworkGetDetailResponse;
import com.hiddenartist.backend.global.config.CustomDataJpaTest;
import com.hiddenartist.backend.global.config.TestDataInitializer;
import com.hiddenartist.backend.global.type.EntityToken;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@CustomDataJpaTest
class ArtworkRepositoryTest {

  @Autowired
  private TestDataInitializer initializer;

  @Autowired
  private ArtworkRepository artworkRepository;

  @Test
  @DisplayName("Token 입력시 Artwork Detail 반환")
  void findArtworkDetailByTokenTest() {
    //given
    initializer.saveArtists(1);
    initializer.saveArtworks(1);

    String token = EntityToken.ARTWORK.identifyToken("1");
    //when
    ArtworkGetDetailResponse artworkDetailByToken = artworkRepository.findArtworkDetailByToken(token);

    //then
    assertThat(artworkDetailByToken).isNotNull()
                                    .extracting("name", "image", "width", "height", "medium", "productionYear",
                                        "description")
                                    .containsExactly("Artwork1", "Test Artwork Image1", 21.1, 31.5, "유화", 2001,
                                        "Test Artwork Description1");
    assertThat(artworkDetailByToken.artists()).hasSize(1)
                                              .extracting("name", "token")
                                              .containsExactly(tuple
                                                  ("artist1", "1")
                                              );
  }

}