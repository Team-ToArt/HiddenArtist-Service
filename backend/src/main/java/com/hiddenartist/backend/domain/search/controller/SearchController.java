package com.hiddenartist.backend.domain.search.controller;

import com.hiddenartist.backend.domain.search.controller.response.SearchAllResponse;
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
  public SearchAllResponse searchKeyword(@PathVariable("keyword") String keyword) {
    return searchService.searchAllByKeyword(keyword);
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