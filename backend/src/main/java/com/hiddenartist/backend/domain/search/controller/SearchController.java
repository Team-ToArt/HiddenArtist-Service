package com.hiddenartist.backend.domain.search.controller;

import com.hiddenartist.backend.domain.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class SearchController {

  private final SearchService searchService;

  @GetMapping("/{keyword}")
  public void searchKeyword(@PathVariable("keyword") String keyword) {
    // 검색 엔진 처럼 검색
    // keyword를 통해서 artist, artwork, exhibition 제목 기준 필터링 해서 검색
    // 조회 데이터가 없으면 Empty List 반환
    searchService.searchAllByKeyword(keyword);
  }

  @GetMapping("/artists/{keyword}")
  public void searchArtistByKeyword(@PathVariable("keyword") String keyword) {
    // Artist 검색
  }

  @GetMapping("/artworks/{keyword}")
  public void searchArtworkByKeyword(@PathVariable("keyword") String keyword) {
    // Artwork 검색
  }

  @GetMapping("/exhibitions/{keyword}")
  public void searchExhibitionByKeyword(@PathVariable("keyword") String keyword) {
    // Exhibition 검색
  }

  @GetMapping("/genres/{keyword}")
  public void searchGenreByKeyword(@PathVariable("keyword") String keyword) {
    // Genre 검색 (ADMIN 전용)
  }


}