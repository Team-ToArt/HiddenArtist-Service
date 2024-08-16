package com.pop.backend.domain.account.persistence;

import static com.pop.backend.domain.artist.persistence.type.ContactType.EMAIL;
import static com.pop.backend.domain.artist.persistence.type.ContactType.SNS;
import static com.pop.backend.domain.artist.persistence.type.ContactType.TEL;
import static org.assertj.core.api.Assertions.assertThat;

import com.pop.backend.domain.artist.controller.response.ArtistGetDetailResponse;
import com.pop.backend.domain.artist.controller.response.ArtistGetDetailResponse.ContactInfo;
import com.pop.backend.domain.artist.controller.response.ArtistSimpleResponse;
import com.pop.backend.domain.artist.persistence.Artist;
import com.pop.backend.domain.artist.persistence.ArtistContact;
import com.pop.backend.domain.artist.persistence.repository.ArtistRepository;
import com.pop.backend.domain.artist.persistence.type.ContactType;
import com.pop.backend.domain.genre.persistence.Genre;
import com.pop.backend.global.CustomDataJpaTest;
import com.pop.backend.global.type.EntityToken;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.jdbc.core.JdbcTemplate;

@CustomDataJpaTest
public class ArtistTest {

  private static final String TOKEN = "1";

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Autowired
  private ArtistRepository artistRepository;

  @BeforeEach
  void init() {
    saveDummyArtists();
    Long artistId = jdbcTemplate.queryForObject("select a.id from artist as a where token = ?",
        Long.class, EntityToken.ARTIST.identifyToken(TOKEN));
    saveDummyArtistContacts(artistId);
    saveDummyGenres();
    List<Long> genres = jdbcTemplate.queryForList("select g.id from genre as g", Long.class);
    saveDummyArtistGenre(artistId, genres);
  }

  @Test
  @DisplayName("작가 전체 조회 테스트")
  void getAllArtistsTest() {
    //given
    Pageable pageRequest = PageRequest.of(1, 12, Sort.by(Direction.ASC, "name"));
    //when
    Page<ArtistSimpleResponse> artists = artistRepository.findAllArtists(pageRequest);
    //then
    assertThat(artists).hasSize(12);
    assertThat(artists.getTotalPages()).isEqualTo(10);
    assertThat(artists.getPageable().getPageNumber()).isEqualTo(1);
  }

  @Test
  @DisplayName("작가 상세정보 조회 테스트")
  void getArtistDetailTest() {
    //given
    Map<ContactType, List<ContactInfo>> map = new HashMap<>() {{
      Arrays.stream(ContactType.values()).forEach(contactType -> put(contactType, new ArrayList<>()));
      get(SNS).addAll(List.of(new ContactInfo("instagram", "instagramUrl"), new ContactInfo("x(twitter)", "twitterUrl"),
          new ContactInfo("github", "githubUrl")));
      get(EMAIL).add(new ContactInfo("email", "test@test.com"));
      get(TEL).add(new ContactInfo("tel", "010-1234-5678"));
    }};

    List<String> genres = List.of("현대미술", "조소", "추상화");
    //when
    ArtistGetDetailResponse response = artistRepository.findArtistDetailByToken(
        EntityToken.ARTIST.identifyToken(TOKEN));

    //then
    assertThat(response).extracting("name", "image", "birth", "summary", "description", "genres", "contacts")
                        .containsExactly(
                            "artist1", "test profile image 1", LocalDate.of(1991, 2, 2),
                            "test summary 1", "test description 1", genres, map
                        );
  }

  private void saveDummyArtists() {
    List<Artist> artists = new ArrayList<>();
    IntStream.rangeClosed(1, 120)
             .mapToObj(this::createArtist)
             .forEach(artists::add);
    String sql = "insert into artist (name,token,birth,summary,description,profile_image) values (?,?,?,?,?,?)";
    jdbcTemplate.batchUpdate(sql, artists, 100, this::addRow);
  }

  private void saveDummyArtistContacts(Long artistId) {
    String artistContactSql = "insert into artist_contact (type,label,contact_value,artist_id) values (?,?,?,?)";
    List<ArtistContact> artistContacts = createArtistContacts();
    jdbcTemplate.batchUpdate(artistContactSql, artistContacts, artistContacts.size(), (ps, artistContact) -> {
      ps.setString(1, artistContact.getType().name());
      ps.setString(2, artistContact.getLabel());
      ps.setString(3, artistContact.getContactValue());
      ps.setLong(4, artistId);
    });
  }

  private void saveDummyGenres() {
    List<Genre> genres = List.of(new Genre("현대미술"), new Genre("조소"), new Genre("추상화"));
    jdbcTemplate.batchUpdate("insert into genre (name) values (?)", genres, genres.size(),
        (ps, genre) -> ps.setString(1, genre.getName()));
  }

  private void saveDummyArtistGenre(Long artistId, List<Long> genreIds) {
    String sql = "insert into artist_genre (artist_id,genre_id) values (?,?)";
    jdbcTemplate.batchUpdate(sql, genreIds, genreIds.size(), (ps, genreId) -> {
      ps.setLong(1, artistId);
      ps.setLong(2, genreId);
    });
  }

  private Artist createArtist(int count) {
    int year = 1990 + (count % 20);
    int month = (count % 12) + 1;
    int days = (count % 30) + 1;
    return Artist.builder()
                 .name("artist" + count)
                 .profileImage("test profile image " + count)
                 .token("Artist_" + count)
                 .birth(LocalDate.of(year, month, days))
                 .summary("test summary " + count)
                 .description("test description " + count)
                 .build();
  }

  void addRow(PreparedStatement ps, Artist artist) throws SQLException {
    ps.setString(1, artist.getName());
    ps.setString(2, artist.getToken());
    ps.setDate(3, Date.valueOf(artist.getBirth()));
    ps.setString(4, artist.getSummary());
    ps.setString(5, artist.getDescription());
    ps.setString(6, artist.getProfileImage());
  }

  private List<ArtistContact> createArtistContacts() {
    ArtistContact contact1 = ArtistContact.builder()
                                          .type(ContactType.SNS)
                                          .label("instagram")
                                          .contactValue("instagramUrl")
                                          .build();
    ArtistContact contact2 = ArtistContact.builder()
                                          .type(ContactType.SNS)
                                          .label("x(twitter)")
                                          .contactValue("twitterUrl")
                                          .build();
    ArtistContact contact3 = ArtistContact.builder()
                                          .type(ContactType.SNS)
                                          .label("github")
                                          .contactValue("githubUrl")
                                          .build();
    ArtistContact contact4 = ArtistContact.builder()
                                          .type(EMAIL)
                                          .label("email")
                                          .contactValue("test@test.com")
                                          .build();
    ArtistContact contact5 = ArtistContact.builder()
                                          .type(ContactType.TEL)
                                          .label("tel")
                                          .contactValue("010-1234-5678")
                                          .build();
    return List.of(contact1, contact2, contact3, contact4, contact5);
  }

}