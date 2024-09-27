package com.hiddenartist.backend.domain.exhibition.controller;

import static org.springframework.data.domain.Sort.Direction.ASC;

import com.hiddenartist.backend.domain.exhibition.controller.response.ExhibitionDetailResponse;
import com.hiddenartist.backend.domain.exhibition.controller.response.ExhibitionGetListResponse;
import com.hiddenartist.backend.domain.exhibition.controller.response.ExhibitionGetPageResponse;
import com.hiddenartist.backend.domain.exhibition.service.ExhibitionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/exhibitions")
@RequiredArgsConstructor
public class ExhibitionController {

  private final ExhibitionService exhibitionService;

  @GetMapping
  public ExhibitionGetPageResponse getAllExhibitions(
      @PageableDefault(page = 1, size = 16, sort = {"end_date", "name"}, direction = ASC) Pageable pageable) {
    return exhibitionService.findAllExhibitions(pageable);
  }

  @GetMapping("/{token}")
  public ExhibitionDetailResponse getExhibitionDetail(@PathVariable("token") String token) {
    return exhibitionService.findExhibitionDetail(token);
  }

  @GetMapping("/current")
  public ExhibitionGetListResponse getCurrentExhibitions() {
    return exhibitionService.findCurrentExhibitions();
  }

  @GetMapping("/upcoming")
  public ExhibitionGetListResponse getUpcomingExhibitions() {
    return exhibitionService.findUpcomingExhibitions();
  }

  // 마감된 전시회 조회
  @GetMapping("/past")
  public void getPastExhibitions(@PageableDefault(sort = {"end_date", "name"}, direction = ASC) Pageable pageable) {
    // end_date 가 오늘 날짜 미만인 데이터 조회
    // Pagination 적용
  }

}