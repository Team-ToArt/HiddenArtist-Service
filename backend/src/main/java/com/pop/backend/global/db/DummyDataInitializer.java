package com.pop.backend.global.db;

import com.pop.backend.domain.artist.persistence.Artist;
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
    int result = insertDummyData();
    log.info("DummyData Initializing End. update row: {}", result);
  }

  private int insertDummyData() {
    List<Artist> artists = IntStream.rangeClosed(1, 500).mapToObj(this::create).toList();
    String sql = "insert into artist (name,summary,description,birth,token,create_date,update_date) values (?,?,?,?,?,?,?)";
    int[][] update = jdbcTemplate.batchUpdate(sql, artists, artists.size(), this::fillColumns);

    return Arrays.stream(update).flatMapToInt(Arrays::stream).sum();
  }

  private Artist create(int count) {
    int year = 1990 + (count % 20);
    int month = (count % 12) + 1;
    int days = (count % 30) + 1;
    return Artist.builder()
                 .name(RandomNameGenerator.getRandomName())
                 .birth(LocalDate.of(year, month, days))
                 .token(EntityToken.ARTIST.randomCharacterWithPrefix())
                 .description("test description " + count)
                 .summary("test summary " + count)
                 .build();
  }

  private void fillColumns(PreparedStatement ps, Artist artist) throws SQLException {
    ps.setString(1, artist.getName());
    ps.setString(2, artist.getSummary());
    ps.setString(3, artist.getDescription());
    ps.setDate(4, Date.valueOf(artist.getBirth()));
    ps.setString(5, artist.getToken());
    ps.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
    ps.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
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