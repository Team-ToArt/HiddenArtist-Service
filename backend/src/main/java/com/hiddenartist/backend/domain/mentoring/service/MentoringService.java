package com.hiddenartist.backend.domain.mentoring.service;

import com.hiddenartist.backend.domain.mentoring.controller.response.MentoringSimpleResponse;
import com.hiddenartist.backend.domain.mentoring.persistence.Mentoring;
import com.hiddenartist.backend.domain.mentoring.persistence.repository.MentoringRepository;
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

  @Transactional(readOnly = true)
  public Page<MentoringSimpleResponse> getAllMentorings(Pageable pageable) {
    Pageable pageRequest = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort());
    Page<Mentoring> mentorings = mentoringRepository.findAllMentorings(pageRequest);
    return mentorings.map(MentoringSimpleResponse::create);
  }

}