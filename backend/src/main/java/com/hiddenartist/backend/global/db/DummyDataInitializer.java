package com.hiddenartist.backend.global.db;

import static com.hiddenartist.backend.domain.artist.persistence.type.ContactType.EMAIL;

import com.hiddenartist.backend.domain.account.persistence.Account;
import com.hiddenartist.backend.domain.account.persistence.type.ProviderType;
import com.hiddenartist.backend.domain.account.persistence.type.Role;
import com.hiddenartist.backend.domain.artist.persistence.Artist;
import com.hiddenartist.backend.domain.artist.persistence.ArtistContact;
import com.hiddenartist.backend.domain.artist.persistence.type.ContactType;
import com.hiddenartist.backend.domain.artwork.persistence.Artwork;
import com.hiddenartist.backend.domain.genre.persistence.Genre;
import com.hiddenartist.backend.global.type.EntityToken;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
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
  private final long ARTIST_ID = 1L;
  private final List<Long> LIST = List.of(1L, 2L, 3L);

  @Transactional
  @EventListener(ApplicationReadyEvent.class)
  protected void init() {
    log.info("DummyData Initializing Start.");
    insertDummyData();
    log.info("DummyData Initializing End.");
  }

  private void insertDummyData() {
    saveDummyAccounts();
    saveDummyArtists();
    saveDummyArtistContacts();
    saveDummyGenres();
    saveDummyArtistGenre();
    saveDummyFollowArtist();
    saveDummyArtworks();
    saveDummySignatureArtworks();
    saveDummyArtistArtwork();
  }

  private void saveDummyAccounts() {
    List<Account> accounts = IntStream.rangeClosed(1, 20).mapToObj(this::createDummyAccount).toList();
    String sql = "insert into account (email,nickname,role,provider_type) values (?,?,?,?)";
    jdbcTemplate.batchUpdate(sql, accounts, accounts.size(), (ps, account) -> {
      ps.setString(1, account.getEmail());
      ps.setString(2, account.getNickname());
      ps.setString(3, account.getRole().name());
      ps.setString(4, account.getProviderType().name());
    });
  }

  private void saveDummyArtists() {
    List<Artist> artists = IntStream.rangeClosed(1, 500).mapToObj(this::createDummyArtist).toList();
    String sql = "insert into artist (name,token,birth,summary,description,profile_image) values (?,?,?,?,?,?)";
    jdbcTemplate.batchUpdate(sql, artists, artists.size(), (ps, artist) -> {
      ps.setString(1, artist.getName());
      ps.setString(2, artist.getToken());
      ps.setDate(3, Date.valueOf(artist.getBirth()));
      ps.setString(4, artist.getSummary());
      ps.setString(5, artist.getDescription());
      ps.setString(6, artist.getProfileImage());
    });
  }

  private void saveDummyArtistContacts() {
    List<ArtistContact> artistContacts = createArtistContacts();
    String sql = "insert into artist_contact (type,label,contact_value,artist_id) values (?,?,?,?)";
    jdbcTemplate.batchUpdate(sql, artistContacts, artistContacts.size(), (ps, contact) -> {
      ps.setString(1, contact.getType().name());
      ps.setString(2, contact.getLabel());
      ps.setString(3, contact.getContactValue());
      ps.setLong(4, ARTIST_ID);
    });
  }

  private void saveDummyGenres() {
    List<Genre> genres = Stream.of("현대미술", "조소", "추상화").map(Genre::new).toList();
    String sql = "insert into genre (name) values (?)";
    jdbcTemplate.batchUpdate(sql, genres, genres.size(), (ps, genre) -> {
      ps.setString(1, genre.getName());
    });
  }

  private void saveDummyArtistGenre() {
    String sql = "insert into artist_genre (artist_id,genre_id) values (?,?)";
    jdbcTemplate.batchUpdate(sql, LIST, LIST.size(), (ps, genreId) -> {
      ps.setLong(1, ARTIST_ID);
      ps.setLong(2, genreId);
    });
  }

  private void saveDummyFollowArtist() {
    List<Long> accountIds = LongStream.rangeClosed(1, 20).boxed().toList();
    String sql = "insert into follow_artist (account_id,artist_id) values (?,?)";
    Random random = new Random();
    jdbcTemplate.batchUpdate(sql, accountIds, accountIds.size(), (ps, accountId) -> {
      Set<Long> artistIds = random.longs(1, 501)
                                  .distinct()
                                  .limit(50)
                                  .boxed().collect(Collectors.toSet());
      for (Long artistId : artistIds) {
        ps.setLong(1, accountId);
        ps.setLong(2, artistId);
        ps.addBatch();
      }
    });
  }

  private void saveDummyArtworks() {
    List<Artwork> artworks = IntStream.rangeClosed(1, 20).mapToObj(this::createDummyArtwork).toList();
    String sql = "insert into artwork (name,image,description,token) values (?,?,?,?)";
    jdbcTemplate.batchUpdate(sql, artworks, artworks.size(), (ps, artwork) -> {
      ps.setString(1, artwork.getName());
      ps.setString(2, artwork.getImage());
      ps.setString(3, artwork.getDescription());
      ps.setString(4, artwork.getToken());
    });
  }

  private void saveDummyArtistArtwork() {
    String sql = "select id from artwork where 1 <= id and id <=10";
    List<Long> artworkIds = jdbcTemplate.queryForList(sql, Long.class);
    sql = "insert into artist_artwork (artist_id,artwork_id) values (?,?)";
    jdbcTemplate.batchUpdate(sql, artworkIds, artworkIds.size(), (ps, artworkId) -> {
      ps.setLong(1, ARTIST_ID);
      ps.setLong(2, artworkId);
    });
  }

  private void saveDummySignatureArtworks() {
    String sql = "insert into signature_artwork (artwork_id,display_order) values (?,?) ";
    jdbcTemplate.batchUpdate(sql, LIST, LIST.size(), (ps, id) -> {
      ps.setLong(1, id);
      ps.setByte(2, (byte) (id - 1));
    });
  }

  private Account createDummyAccount(int count) {
    String email = "test" + count + "@test.com";
    String nickname = "test account " + count;
    return Account.builder().providerType(ProviderType.KAKAO).role(Role.USER).email(email).nickname(nickname).build();
  }

  private Artist createDummyArtist(int count) {
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

  private Artwork createDummyArtwork(int count) {
    return Artwork.builder()
                  .name("Artwork" + count)
                  .image("Test Artwork Image" + count)
                  .description("Test Artwork Description" + count)
                  .token(EntityToken.ARTWORK.randomCharacterWithPrefix())
                  .build();
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