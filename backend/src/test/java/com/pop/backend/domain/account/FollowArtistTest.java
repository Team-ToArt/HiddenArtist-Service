package com.pop.backend.domain.account;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import com.pop.backend.domain.account.persistence.Account;
import com.pop.backend.domain.account.persistence.repository.AccountRepository;
import com.pop.backend.domain.account.persistence.type.ProviderType;
import com.pop.backend.domain.account.persistence.type.Role;
import com.pop.backend.domain.artist.persistence.Artist;
import com.pop.backend.domain.artist.persistence.FollowArtist;
import com.pop.backend.domain.artist.persistence.repository.ArtistRepository;
import com.pop.backend.domain.artist.persistence.repository.FollowArtistRepository;
import com.pop.backend.global.TestConfig;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@ActiveProfiles("test")
@Import(TestConfig.class)
@Transactional
public class FollowArtistTest {

  private final Logger logger = LoggerFactory.getLogger(FollowArtistTest.class);

  private final String email = "test@test.com";
  @Autowired
  private ArtistRepository artistRepository;
  @Autowired
  private AccountRepository accountRepository;
  @Autowired
  private FollowArtistRepository followArtistRepository;

  @BeforeEach
  void init() {
    logger.info("======================== Test Init Start ========================");
    Account account = Account.builder().email(email)
                             .role(Role.USER)
                             .providerType(ProviderType.KAKAO)
                             .build();
    accountRepository.saveAndFlush(account);

    Artist artist1 = Artist.builder()
                           .name("artist1")
                           .description("test description1")
                           .token("token1")
                           .build();
    Artist artist2 = Artist.builder()
                           .name("artist2")
                           .description("test description2")
                           .token("token2")
                           .build();
    Artist artist3 = Artist.builder()
                           .name("artist3")
                           .description("test description3")
                           .token("token3")
                           .build();
    artistRepository.saveAllAndFlush(List.of(artist1, artist2, artist3));

    FollowArtist followArtist1 = FollowArtist.builder().account(account).artist(artist1).build();
    FollowArtist followArtist2 = FollowArtist.builder().account(account).artist(artist2).build();
    FollowArtist followArtist3 = FollowArtist.builder().account(account).artist(artist3).build();
    followArtistRepository.saveAllAndFlush(List.of(followArtist1, followArtist2, followArtist3));
    logger.info("======================== Test Init end ========================");
  }

  @Test
  @DisplayName("Follow Artist 조회 테스트")
  void getFollowArtistListTest() {
    List<Artist> artists = artistRepository.findFollowArtistListByAccountEmail(email);

    assertThat(artists).extracting("name", "description")
                       .containsExactly(
                           tuple("artist1", "test description1"),
                           tuple("artist2", "test description2"),
                           tuple("artist3", "test description3")
                       );
  }

  @Test
  @DisplayName("Follow Artist 삭제 테스트")
  void deleteFollowArtistsTest() {
    //given
    List<String> removeTokens = List.of("token1", "token3");

    //when
    accountRepository.removeFollowArtists(email, removeTokens);
    List<Artist> artists = artistRepository.findFollowArtistListByAccountEmail(email);
    //then
    assertThat(artists).hasSize(1).extracting("name", "description")
                       .containsExactly(
                           tuple("artist2", "test description2")
                       );
  }
}