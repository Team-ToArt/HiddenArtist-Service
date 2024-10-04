package com.hiddenartist.backend.domain.mentor.controller;

import com.hiddenartist.backend.domain.mentor.service.MentorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mentors")
@RequiredArgsConstructor
public class MentorController {

  private final MentorService mentorService;

  @GetMapping("/me/mentorings")
  public void getReceivedMentoringApplications(
      @PageableDefault(size = 20, page = 1, sort = "applicationTime", direction = Direction.DESC) Pageable pageable,
      @AuthenticationPrincipal String email
  ) {
    // 신청받은 멘토링 내역 조회
  }

}