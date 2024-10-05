package com.hiddenartist.backend.domain.mentoring.persistence.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.hiddenartist.backend.domain.account.persistence.Account;
import com.hiddenartist.backend.domain.account.persistence.type.Role;
import com.hiddenartist.backend.domain.mentor.persistence.Mentor;
import com.hiddenartist.backend.domain.mentor.persistence.type.Career;
import com.hiddenartist.backend.domain.mentor.persistence.type.CertificationStatus;
import com.hiddenartist.backend.domain.mentoring.persistence.Mentoring;
import com.hiddenartist.backend.domain.mentoring.persistence.type.MentoringStatus;
import com.hiddenartist.backend.global.config.AbstractMySQLRepositoryTest;
import java.util.List;
import java.util.stream.LongStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.jdbc.core.JdbcTemplate;

class MentoringRepositoryTest extends AbstractMySQLRepositoryTest {

  @Autowired
  private MentoringRepository mentoringRepository;

  @Autowired
  private JdbcTemplate jdbcTemplate;

//  MentoringDetailResponse findMentoringByToken(String token);
//  List<MentoringApplication> findMentoringApplicationByMonth(String token, LocalDate selectMonth);

  @Test
  @DisplayName("멘토링 전체조회 테스트")
  void getAllMentoringsTest() {
    //given
    initMentoringData();
    Pageable pageRequest = PageRequest.of(0, 16, Sort.by(Direction.DESC, "totalApplicationCount"));

    //when
    Page<Mentoring> mentorings = mentoringRepository.findAllMentorings(pageRequest);

    //then
    assertThat(mentorings.getContent()).hasSize(16);
  }

  private void initMentoringData() {
    List<Account> mentorAccounts = LongStream.rangeClosed(1, 20)
                                             .mapToObj(id ->
                                                 Account.builder()
                                                        .email("mentor" + id + "@test.com")
                                                        .profileImage("test image" + id)
                                                        .nickname("mentor" + id)
                                                        .role(Role.MENTOR)
                                                        .build())
                                             .toList();
    String sql = "insert into account (id,email,profile_image,nickname,role) values(?,?,?,?,?)";
    jdbcTemplate.batchUpdate(sql, mentorAccounts, mentorAccounts.size(), (ps, mentorAccount) -> {
      ps.setLong(1, mentorAccounts.indexOf(mentorAccount) + 1);
      ps.setString(2, mentorAccount.getEmail());
      ps.setString(3, mentorAccount.getProfileImage());
      ps.setString(4, mentorAccount.getNickname());
      ps.setString(5, mentorAccount.getRole().name());
    });

    List<Mentor> mentors = LongStream.rangeClosed(1, 20)
                                     .mapToObj(id -> Mentor.builder()
                                                           .career(Career.MIDDLE)
                                                           .contactEmail("mentor" + id + "@test.com")
                                                           .organization("test organization" + id)
                                                           .build())
                                     .toList();

    sql = "insert into mentor (id,career,contact_email,organization,certification_status,account_id) values(?,?,?,?,?,?)";
    jdbcTemplate.batchUpdate(sql, mentors, mentors.size(), (ps, mentor) -> {
      ps.setLong(1, mentors.indexOf(mentor) + 1);
      ps.setString(2, mentor.getCareer().name());
      ps.setString(3, mentor.getContactEmail());
      ps.setString(4, mentor.getOrganization());
      ps.setString(5, CertificationStatus.VERIFIED.name());
      ps.setLong(6, mentors.indexOf(mentor) + 1);
    });

    List<Mentoring> mentorings = LongStream.rangeClosed(1, 20)
                                           .mapToObj(
                                               id -> Mentoring.builder()
                                                              .token("test_" + id)
                                                              .name("test mentoring" + id)
                                                              .image("test mentoring image" + id)
                                                              .mentoringStatus(MentoringStatus.OPEN)
                                                              .amount(10000)
                                                              .durationTime("1시간")
                                                              .totalApplicationCount(0L)
                                                              .build())
                                           .toList();
    sql = "insert into mentoring (id,token,name,image,mentoring_status,amount,duration_time,total_application_count,mentor_id) values(?,?,?,?,?,?,?,?,?)";
    jdbcTemplate.batchUpdate(sql, mentorings, mentorings.size(), (ps, mentoring) -> {
      ps.setLong(1, mentorings.indexOf(mentoring) + 1);
      ps.setString(2, mentoring.getToken());
      ps.setString(3, mentoring.getName());
      ps.setString(4, mentoring.getImage());
      ps.setString(5, mentoring.getMentoringStatus().name());
      ps.setInt(6, mentoring.getAmount());
      ps.setString(7, mentoring.getDurationTime());
      ps.setLong(8, mentoring.getTotalApplicationCount());
      ps.setLong(9, mentorings.indexOf(mentoring) + 1);
    });

  }

}