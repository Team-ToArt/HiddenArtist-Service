package com.hiddenartist.backend.domain.artwork.service;

import com.hiddenartist.backend.domain.artwork.controller.response.ArtworkGetDetailResponse;
import com.hiddenartist.backend.domain.artwork.controller.response.ArtworkGetRecommendResponse;
import com.hiddenartist.backend.domain.artwork.persistence.Artwork;
import com.hiddenartist.backend.domain.artwork.persistence.repository.ArtworkRepository;
import com.hiddenartist.backend.global.type.EntityToken;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArtworkService {

  private final ArtworkRepository artworkRepository;


  @Transactional(readOnly = true)
  public ArtworkGetDetailResponse getArtworkDetail(String tokenValue) {
    String token = EntityToken.ARTWORK.identifyToken(tokenValue);
    return artworkRepository.findArtworkDetailByToken(token);
  }

  @Transactional(readOnly = true)
  public ArtworkGetRecommendResponse getArtworkRecommend() {
    List<Artwork> artworkRecommend = artworkRepository.findArtworkRecommend();
    return ArtworkGetRecommendResponse.create(artworkRecommend);
  }

}