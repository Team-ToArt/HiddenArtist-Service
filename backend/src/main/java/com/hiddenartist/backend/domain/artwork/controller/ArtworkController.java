package com.hiddenartist.backend.domain.artwork.controller;

import com.hiddenartist.backend.domain.artwork.controller.response.ArtworkGetDetailResponse;
import com.hiddenartist.backend.domain.artwork.service.ArtworkService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/artworks")
@RequiredArgsConstructor
public class ArtworkController {

  private final ArtworkService artworkService;


  @GetMapping("/{token}")
  public ArtworkGetDetailResponse getArtworkDetail(@PathVariable("token") String token) {
    return artworkService.getArtworkDetail(token);
  }

}