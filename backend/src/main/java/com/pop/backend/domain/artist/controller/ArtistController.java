package com.pop.backend.domain.artist.controller;

import com.pop.backend.domain.artist.controller.response.ArtistGetDetailResponse;
import com.pop.backend.domain.artist.controller.response.ArtistGetListResponse;
import com.pop.backend.domain.artist.controller.response.ArtistGetThreeResponse;
import com.pop.backend.domain.artist.service.ArtistService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/artists")
@RequiredArgsConstructor
public class ArtistController {

  private final ArtistService artistService;

  /**
   * 요청 예시: /api/artists?page=1&size=12&sort=name,asc
   */
  @GetMapping
  public ArtistGetListResponse getAllArtists(
      @PageableDefault(page = 1, size = 12, sort = {"name", "birth"}, direction = Direction.ASC) Pageable pageable) {
    return artistService.getAllArtists(pageable);
  }

  @GetMapping("/{token}")
  public ArtistGetDetailResponse getArtistDetail(@PathVariable("token") String token) {
    return artistService.getArtistDetail(token);
  }

  @GetMapping("/popular")
  public ArtistGetThreeResponse getPopularArtists() {
    return artistService.getPopularArtists();
  }

  @GetMapping("/new")
  public ArtistGetThreeResponse getNewArtists() {
    return artistService.getNewArtists();
  }

}