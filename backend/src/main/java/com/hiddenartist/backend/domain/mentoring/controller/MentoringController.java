package com.hiddenartist.backend.domain.mentoring.controller;

import com.hiddenartist.backend.domain.mentoring.controller.response.MentoringSimpleResponse;
import com.hiddenartist.backend.domain.mentoring.service.MentoringService;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mentorings")
@RequiredArgsConstructor
public class MentoringController {

  private final MentoringService mentoringService;

  // 멘토링 전체 조회
  @GetMapping
  public Page<MentoringSimpleResponse> getAllMentorings(
      @PageableDefault(size = 16, page = 1, sort = "totalApplicationCount", direction = Direction.DESC) Pageable pageable
  ) {
    return mentoringService.getAllMentorings(pageable);
  }

  // 멘토링 상세 조회
  @GetMapping("/{token}/details")
  public void getMentoringDetails(@PathVariable("token") String token) {
    // 멘토링 제목, 멘토 프로필 이미지, 닉네임, 분야, 경력, 조직, 멘토링 상세내용, 금액, 진행시간, 리뷰(존재시)
  }

  // 멘토링 시간 조회
  @GetMapping("/{token}/available")
  public void getMentoringAvailableTimes(@PathVariable("token") String token, @RequestParam("date") LocalDate selectDate) {
    // 해당 멘토링에 대해 selectDate에 해당하는 mentoring_application 조회
    // redis에서 해당 멘토링에 대해 신청 잠금처리된 시간대 조회
    // application_time을 오름차순 정렬 후 반환
  }

  // 멘토링 신청 시간 잠금
  @PostMapping("/{token}/lock")
  public void lockMentoringApplicationTime(@PathVariable("token") String token) {
    // Request Body에 application_time 전달
    // redis에 application_time을 저장.
    // key : Mentoring_{token}
    // value: 기존에 존재한 Set 에 application_time 추가
  }

  // 잠금처리된 멘토링 신청시간 해제
  @PostMapping("/{token}/unlock")
  public void unlockMentoringApplicationTime(@PathVariable("token") String token) {
    // Request Body에 application_time 전달
    // redis에 저장된 application_time 삭제
  }

  // 멘토링 등록
  @PostMapping
  public void registrationMentoring() {

  }

  // 멘토링 수정
  @PutMapping("/{token}")
  public void updateMentoringDetails(@PathVariable("token") String token) {

  }

  // 멘토링 삭제
  @DeleteMapping("/{token}")
  public void deleteMentoring(@PathVariable("token") String token) {

  }

}