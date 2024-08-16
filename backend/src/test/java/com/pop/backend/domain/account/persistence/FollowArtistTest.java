package com.pop.backend.domain.account.persistence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import com.pop.backend.domain.account.persistence.repository.AccountRepository;
import com.pop.backend.domain.account.persistence.type.ProviderType;
import com.pop.backend.domain.account.persistence.type.Role;
import com.pop.backend.domain.artist.persistence.Artist;
import com.pop.backend.domain.artist.persistence.FollowArtist;
import com.pop.backend.domain.artist.persistence.repository.ArtistRepository;
import com.pop.backend.domain.artist.persistence.repository.FollowArtistRepository;
import com.pop.backend.global.CustomDataJpaTest;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@CustomDataJpaTest
public class FollowArtistTest {

  private final Logger logger = LoggerFactory.getLogger(FollowArtistTest.class);

  private final String email = "test@test.com";
  private final String artist1Name = "apple";
  private final String artist2Name = "banana";
  private final String artist3Name = "cherry";
  private final String artist4Name = "dragonFruit";
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
    Account account2 = Account.builder()
                              .email("test2@test.com")
                              .role(Role.USER)
                              .providerType(ProviderType.KAKAO)
                              .build();
    Account account3 = Account.builder()
                              .email("test3@test.com")
                              .role(Role.USER)
                              .providerType(ProviderType.KAKAO)
                              .build();
    accountRepository.saveAllAndFlush(List.of(account, account2, account3));

    Artist artist1 = Artist.builder()
                           .name(artist1Name)
                           .description("test description1")
                           .token("token1")
                           .build();
    Artist artist2 = Artist.builder()
                           .name(artist2Name)
                           .description("test description2")
                           .token("token2")
                           .build();
    Artist artist3 = Artist.builder()
                           .name(artist3Name)
                           .description("test description3")
                           .token("token3")
                           .build();
    Artist artist4 = Artist.builder()
                           .name(artist4Name)
                           .description("test description4")
                           .token("token4")
                           .build();
    artistRepository.saveAllAndFlush(List.of(artist1, artist2, artist3, artist4));

    FollowArtist followArtist1 = FollowArtist.builder().account(account).artist(artist1).build();
    FollowArtist followArtist2 = FollowArtist.builder().account(account).artist(artist2).build();
    FollowArtist followArtist3 = FollowArtist.builder().account(account).artist(artist3).build();
    FollowArtist followArtist4 = FollowArtist.builder().account(account2).artist(artist3).build();
    FollowArtist followArtist5 = FollowArtist.builder().account(account3).artist(artist2).build();
    FollowArtist followArtist6 = FollowArtist.builder().account(account3).artist(artist3).build();
    followArtistRepository.saveAllAndFlush(
        List.of(followArtist1, followArtist2, followArtist3, followArtist4, followArtist5,
            followArtist6));
    logger.info("======================== Test Init end ========================");
  }

  @Test
  @DisplayName("Follow Artist 조회 테스트")
  void getFollowArtistListTest() {
    List<Artist> artists = artistRepository.findFollowArtistListByAccountEmail(email);

    assertThat(artists).extracting("name", "description")
                       .containsExactly(
                           tuple(artist1Name, "test description1"),
                           tuple(artist2Name, "test description2"),
                           tuple(artist3Name, "test description3")
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
                           tuple(artist2Name, "test description2")
                       );
  }

  @Test
  @DisplayName("Popular Artist 조회 테스트")
  void getPopularArtistsTest() {
    //given
    //when
    List<Artist> popularArtists = artistRepository.findPopularArtists();
    //then
    assertThat(popularArtists)
        .hasSize(3)
        .extracting("name")
        .containsExactly(
            artist3Name, artist2Name, artist1Name
        );
  }

  /* 해당 테스트는 더미 데이터 생성 로직을 수정하고 다시 작성하겠습니다.*/
//  @Test
//  @DisplayName("New Artist 조회 테스트")
//  void getNewArtistsTest() {
//    //given
//    //when
//    List<Artist> newArtists = artistRepository.findNewArtists();
//    //then
//    assertThat(newArtists).hasSize(3)
//                          .extracting("name", "description")
//                          .containsExactly(
//                              tuple(artist4Name, "test description4"),
//                              tuple(artist3Name, "test description3"),
//                              tuple(artist2Name, "test description2")
//                          );
//
//  }

}