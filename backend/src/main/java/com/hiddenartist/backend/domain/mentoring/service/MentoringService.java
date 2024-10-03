package com.hiddenartist.backend.domain.mentoring.service;

import com.hiddenartist.backend.domain.mentoring.controller.response.MentoringDetailResponse;
import com.hiddenartist.backend.domain.mentoring.controller.response.MentoringSimpleResponse;
import com.hiddenartist.backend.domain.mentoring.persistence.LockApplicationTime;
import com.hiddenartist.backend.domain.mentoring.persistence.Mentoring;
import com.hiddenartist.backend.domain.mentoring.persistence.repository.MentoringRepository;
import com.hiddenartist.backend.global.redis.LockApplicationTimeClient;
import com.hiddenartist.backend.global.type.EntityToken;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MentoringService {

  private final MentoringRepository mentoringRepository;
  private final LockApplicationTimeClient redisClient;

  @Transactional(readOnly = true)
  public Page<MentoringSimpleResponse> getAllMentorings(Pageable pageable) {
    Pageable pageRequest = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort());
    Page<Mentoring> mentorings = mentoringRepository.findAllMentorings(pageRequest);
    return mentorings.map(MentoringSimpleResponse::create);
  }

  @Transactional(readOnly = true)
  public MentoringDetailResponse getMentoringDetails(String tokenValue) {
    String token = EntityToken.MENTORING.identifyToken(tokenValue);
    return mentoringRepository.findMentoringByToken(token);
  }

  @Transactional
  public void reservationApplicationTime(String tokenValue, LocalDateTime applicationTime, String email) {
    LockApplicationTime lockApplicationTime = new LockApplicationTime(email, tokenValue, applicationTime);
    redisClient.save(lockApplicationTime);
  }

}