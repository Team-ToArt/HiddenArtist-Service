package com.hiddenartist.backend.global;

import static org.assertj.core.api.Assertions.assertThat;

import com.hiddenartist.backend.domain.account.persistence.repository.AccountRepository;
import com.hiddenartist.backend.domain.artist.persistence.Artist;
import com.hiddenartist.backend.domain.artist.persistence.repository.ArtistRepository;
import com.hiddenartist.backend.domain.artwork.persistence.repository.ArtworkRepository;
import com.hiddenartist.backend.global.config.CustomDataJpaTest;
import com.hiddenartist.backend.global.config.TestDataInitializer;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@CustomDataJpaTest
public class DataInitializerTest {

  @Autowired
  TestDataInitializer initializer;

  @Autowired
  AccountRepository accountRepository;

  @Autowired
  ArtistRepository artistRepository;

  @Autowired
  ArtworkRepository artworkRepository;

  @Test
  @DisplayName("이니셜라이저 테스트")
  void initializerTest() {
    //given
    initializer.saveAccounts(20);
    initializer.saveArtists(100);
    initializer.saveArtworks(20);
    initializer.saveSignatureArtworks();
    initializer.saveFollowArtists(20);
    //when
    long accountCount = accountRepository.count();
    long artistCount = artistRepository.count();
    long artworkCount = artworkRepository.count();
    List<Artist> followArtists = accountRepository.findFollowArtistListByEmail("test1@test.com");

    //then
    assertThat(accountCount).isEqualTo(20);
    assertThat(artistCount).isEqualTo(100);
    assertThat(artworkCount).isEqualTo(20);
    assertThat(followArtists).hasSize(20);
  }

}