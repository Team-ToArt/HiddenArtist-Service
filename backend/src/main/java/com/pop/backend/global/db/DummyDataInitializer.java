package com.pop.backend.global.db;

import static com.pop.backend.domain.artist.persistence.type.ContactType.EMAIL;

import com.pop.backend.domain.artist.persistence.Artist;
import com.pop.backend.domain.artist.persistence.ArtistContact;
import com.pop.backend.domain.artist.persistence.type.ContactType;
import com.pop.backend.domain.genre.persistence.Genre;
import com.pop.backend.global.type.EntityToken;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@Profile("local")
@RequiredArgsConstructor
public class DummyDataInitializer {

  private final JdbcTemplate jdbcTemplate;

  @Transactional
  @EventListener(ApplicationReadyEvent.class)
  protected void init() {
    log.info("DummyData Initializing Start.");
    insertDummyData();
    log.info("DummyData Initializing End.");
  }

  private void insertDummyData() {
    Long artistId = saveDummyArtists();
    saveDummyArtistContacts(artistId);
    List<Long> genreIds = saveDummyGenres();
    saveDummyArtistGenre(artistId, genreIds);
  }

  private Long saveDummyArtists() {
    List<Artist> artists = IntStream.rangeClosed(1, 500).mapToObj(this::create).toList();
    String sql = "insert into artist (name,token,birth,summary,description,profile_image,create_date,update_date) values (?,?,?,?,?,?,?,?)";
    jdbcTemplate.batchUpdate(sql, artists, artists.size(), this::fillColumns);
    return jdbcTemplate.queryForObject("select a.id from artist as a where id = 1", Long.class);
  }

  private void saveDummyArtistContacts(Long artistId) {
    List<ArtistContact> artistContacts = createArtistContacts();
    String sql = "insert into artist_contact (type,label,contact_value,artist_id,create_date,update_date) values (?,?,?,?,?,?)";
    jdbcTemplate.batchUpdate(sql, artistContacts, artistContacts.size(), (ps, contact) -> {
      LocalDateTime now = LocalDateTime.now();
      ps.setString(1, contact.getType().name());
      ps.setString(2, contact.getLabel());
      ps.setString(3, contact.getContactValue());
      ps.setLong(4, artistId);
      ps.setTimestamp(5, Timestamp.valueOf(now));
      ps.setTimestamp(6, Timestamp.valueOf(now));
    });
  }

  private List<Long> saveDummyGenres() {
    List<Genre> genres = List.of(new Genre("현대미술"), new Genre("조소"), new Genre("추상화"));
    jdbcTemplate.batchUpdate("insert into genre (name,create_date,update_date) values (?,?,?)", genres, genres.size(),
        (ps, genre) -> {
          ps.setString(1, genre.getName());
          ps.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
          ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
        });
    return jdbcTemplate.queryForList("select g.id from genre as g", Long.class);
  }

  private void saveDummyArtistGenre(Long artistId, List<Long> genreIds) {
    String sql = "insert into artist_genre (artist_id,genre_id,create_date,update_date) values (?,?,?,?)";
    jdbcTemplate.batchUpdate(sql, genreIds, genreIds.size(), (ps, genreId) -> {
      LocalDateTime now = LocalDateTime.now();
      ps.setLong(1, artistId);
      ps.setLong(2, genreId);
      ps.setTimestamp(3, Timestamp.valueOf(now));
      ps.setTimestamp(4, Timestamp.valueOf(now));
    });
  }

  private Artist create(int count) {
    int year = 1990 + (count % 20);
    int month = (count % 12) + 1;
    int days = (count % 30) + 1;
    return Artist.builder()
                 .name(RandomNameGenerator.getRandomName())
                 .profileImage("test profile image " + count)
                 .birth(LocalDate.of(year, month, days))
                 .summary("test summary " + count)
                 .description("test description " + count)
                 .token(EntityToken.ARTIST.randomCharacterWithPrefix())
                 .build();
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

  private void fillColumns(PreparedStatement ps, Artist artist) throws SQLException {
    LocalDateTime now = LocalDateTime.now();
    ps.setString(1, artist.getName());
    ps.setString(2, artist.getToken());
    ps.setDate(3, Date.valueOf(artist.getBirth()));
    ps.setString(4, artist.getSummary());
    ps.setString(5, artist.getDescription());
    ps.setString(6, artist.getProfileImage());
    ps.setTimestamp(7, Timestamp.valueOf(now));
    ps.setTimestamp(8, Timestamp.valueOf(now));
  }

  private static class RandomNameGenerator {

    private static final List<String> lastNames = Arrays.asList(
        "김", "이", "박", "최", "한", "윤", "홍", "백", "조", "장", "추", "정",
        "허", "장", "권", "양", "한", "남궁", "제갈", "표", "고", "오", "배",
        "송", "문", "심", "안", "황보", "구", "류", "허");

    private static final List<String> firstNames = Arrays.asList(
        "원주", "민지", "재현", "동원", "창호", "정윤", "승은", "재영", "영진", "승윤", "현주", "지우", "민하", "기홍", "철", "웅", "훈", "건", "린",
        "예린", "민지", "재범", "동훈", "국", "지혜", "수지", "민기", "성준", "성주", "혜주", "지효", "지원", "형민", "용준", "병철", "규성", "나영", "유리",
        "윤아", "서윤", "서린", "수린", "무영", "동건", "동석", "승헌", "요한", "요셉", "택"
    );

    private static String getRandomName() {
      Collections.shuffle(lastNames);
      Collections.shuffle(firstNames);
      return lastNames.get(0) + firstNames.get(0);
    }
  }

}