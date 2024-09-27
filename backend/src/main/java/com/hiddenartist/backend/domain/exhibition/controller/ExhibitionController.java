package com.hiddenartist.backend.domain.exhibition.controller;

import static org.springframework.data.domain.Sort.Direction.ASC;

import com.hiddenartist.backend.domain.exhibition.controller.response.ExhibitionDetailResponse;
import com.hiddenartist.backend.domain.exhibition.controller.response.ExhibitionGetListResponse;
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

  // 전시회 전체 조회
  @GetMapping
  public ExhibitionGetListResponse getAllExhibitions(
      @PageableDefault(page = 1, size = 16, sort = {"end_date", "name"}, direction = ASC) Pageable pageable) {
    // Pagination 적용 -> size 10, sort end_date asc, name asc
    // end_date 기준 오름차순
    return exhibitionService.findAllExhibitions(pageable);
  }

  // 전시회 상세 조회
  @GetMapping("/{token}")
  public ExhibitionDetailResponse getExhibitionDetail(@PathVariable("token") String token) {
    return exhibitionService.findExhibitionDetail(token);
  }

  // 진행중인 전시회 조회
  @GetMapping("/current")
  public void getCurrentExhibitions() {
    // start_date 가 오늘 날짜 이전이고, end_date 가 오늘 날짜 이후인 데이터 조회
    // end_date 기준 오름차순, limit 10
  }

  // 오픈 예정 전시회 조회
  @GetMapping("/upcoming")
  public void getUpcomingExhibitions() {
    // start_date 가 오늘 날짜 초과인 데이터만 조회
    // start_date 기준 오름차순, limit 10
  }

  // 마감된 전시회 조회
  @GetMapping("/past")
  public void getPastExhibitions(@PageableDefault(sort = {"end_date", "name"}, direction = ASC) Pageable pageable) {
    // end_date 가 오늘 날짜 미만인 데이터 조회
    // Pagination 적용
  }

}