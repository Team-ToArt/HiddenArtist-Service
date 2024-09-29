package com.hiddenartist.backend.domain.search.persistence.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.hiddenartist.backend.domain.artist.persistence.Artist;
import com.hiddenartist.backend.domain.artwork.persistence.Artwork;
import com.hiddenartist.backend.global.config.AbstractMySQLRepositoryTest;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/*
 * Full Text Index는 DB에 Commit이 되어야 생성됩니다.
 * @Transactional이 적용되면 DB에 Commit되지 않고 테스트 종료 시 Rollback되기 때문에
 * 검색 테스트에서 트랜잭션을 비활성화하고, TearDown 메서드를 통해 수동으로 테스트 데이터를 정리합니다.
 * 이렇게 하면 데이터베이스 상태를 유지하면서 Full Text Index의 동작을 검증할 수 있으며,
 * 각 테스트가 독립적으로 실행될 수 있도록 데이터를 일괄 삭제합니다.
 */
class SearchRepositoryTest extends AbstractMySQLRepositoryTest {

  @Autowired
  private SearchRepository searchRepository;

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @AfterEach
  void tearDown() {
    jdbcTemplate.execute("delete from artist");
    jdbcTemplate.execute("delete from artwork");
  }

  // 아티스트 조회
  @Test
  @DisplayName("검색어 입력시 작가 최대 10명 조회")
  @Transactional(propagation = Propagation.NOT_SUPPORTED)
  void searchArtistsByKeywordTest() {
    //given
    saveArtists();
    String keyword = "이현";

    //when
    List<Artist> artists = searchRepository.findArtistByKeyword(keyword);

    artists.forEach(artist -> System.out.println(artist.getName()));

    //then
    assertThat(artists).hasSize(10)
                       .filteredOn(artist -> artist.getName().contains(keyword))
                       .isNotEmpty();
  }

  // 작품 조회
  @Test
  @DisplayName("검색어 입력시 작품 최대 10점 조회")
  @Transactional(propagation = Propagation.NOT_SUPPORTED)
  void searchArtworksByKeywordTest() {
    //given
    saveArtworks();
    String keyword = "별빛";

    //when
    List<Artwork> artworks = searchRepository.findArtworkByKeyword(keyword);

    //then
    assertThat(artworks).hasSize(10)
                        .filteredOn(artwork -> artwork.getName().contains(keyword))
                        .isNotEmpty();
  }

  // 장르 조회
  @Test
  @DisplayName("검색어에 부합하는 장르 조회")
  void searchGenresByKeywordTest() {
    //given

    //when

    //then
  }

  // 전시회 조회
  @Test
  @DisplayName("검색어 입력시 전시회 최대 10개 조회")
  void searchExhibitionsByKeywordTest() {
    //given

    //when

    //then
  }

  // 멘토링 조회
  @Test
  @DisplayName("검색어에 부합하는 멘토링 조회")
  void searchMentoringsByKeywordTest() {
    //given

    //when

    //then
  }

  private void saveArtists() {
    List<String> names = List.of(
        "이현우",
        "김이현",
        "이현수",
        "박이현",
        "이현석",
        "정이현",
        "이현진",
        "최이현",
        "이현지",
        "한이현",
        "이현경",
        "류현진"
    );

    jdbcTemplate.batchUpdate("insert into artist (name) values(?)", names, names.size(), (ps, name) -> {
      ps.setString(1, name);
    });

  }

  private void saveArtworks() {
    List<String> names = List.of(
        "꿈의 별빛",
        "바다 위 별빛",
        "달과 별빛",
        "시간 속의 별빛",
        "별빛의 여정",
        "별빛 속에서",
        "어둠을 비추는 별빛",
        "별빛 아래서",
        "숲을 감싸는 별빛",
        "피 땀 눈물",
        "저녁 하늘 별빛",
        "햇살이 나뭇잎을 핥고 있었다."
    );
    jdbcTemplate.batchUpdate("insert into artwork (name) values(?)", names, names.size(), (ps, name) -> {
      ps.setString(1, name);
    });
  }

}