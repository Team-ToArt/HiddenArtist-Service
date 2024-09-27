package com.hiddenartist.backend.domain.exhibition.service;

import com.hiddenartist.backend.domain.exhibition.controller.response.ExhibitionGetListResponse;
import com.hiddenartist.backend.domain.exhibition.controller.response.ExhibitionSimpleResponse;
import com.hiddenartist.backend.domain.exhibition.persistence.repository.ExhibitionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ExhibitionService {

  private final ExhibitionRepository exhibitionRepository;

  @Transactional(readOnly = true)
  public ExhibitionGetListResponse findAllExhibitions(Pageable pageable) {
    Pageable pageRequest = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort());
    Page<ExhibitionSimpleResponse> exhibitionSimpleResponses = exhibitionRepository.findAllExhibitions(pageRequest);
    return new ExhibitionGetListResponse(exhibitionSimpleResponses);
  }

}