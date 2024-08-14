package com.pop.backend.domain.account;

import static org.assertj.core.api.Assertions.assertThat;

import com.pop.backend.domain.artist.persistence.Artist;
import com.pop.backend.domain.artist.persistence.repository.ArtistRepository;
import com.pop.backend.global.TestConfig;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@DataJpaTest
@Import(value = TestConfig.class)
public class ArtistTest {

  @Autowired
  JdbcTemplate jdbcTemplate;
  @Autowired
  private ArtistRepository artistRepository;

  @BeforeEach
  void init() {
    List<Artist> artists = new ArrayList<>();
    IntStream.rangeClosed(1, 120).mapToObj(this::createArtist).forEach(artists::add);
    String sql = "insert into artist (name,token,birth,create_date,update_date) values (?,?,?,?,?)";
    jdbcTemplate.batchUpdate(sql, artists, 100, this::addRow);
  }

  Artist createArtist(int count) {
    int year = 1990 + (count % 20);
    int month = (count % 12) + 1;
    int days = (count % 30) + 1;
    return Artist.builder()
                 .name("artist" + count)
                 .token("Artist_" + count)
                 .birth(LocalDate.of(year, month, days))
                 .build();
  }

  void addRow(PreparedStatement ps, Artist artist) throws SQLException {
    ps.setString(1, artist.getName());
    ps.setString(2, artist.getToken());
    ps.setDate(3, Date.valueOf(artist.getBirth()));
    ps.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
    ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
  }

  @Test
  @DisplayName("작가 전체 조회 테스트")
  void getAllArtistsTest() {
    //given
    Pageable pageRequest = PageRequest.of(1, 12, Sort.by(Direction.ASC, "name"));
    //when
    Page<Artist> artists = artistRepository.findAllArtists(pageRequest);
    //then
    assertThat(artists).hasSize(12);
    assertThat(artists.getTotalPages()).isEqualTo(10);
    assertThat(artists.getPageable().getPageNumber()).isEqualTo(1);
  }

}