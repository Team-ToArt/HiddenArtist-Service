package com.hiddenartist.backend.domain.artwork.persistence.repository;

import com.hiddenartist.backend.domain.artwork.controller.response.ArtworkGetDetailResponse;
import com.hiddenartist.backend.domain.artwork.persistence.Artwork;
import java.util.List;

public interface CustomArtworkRepository {

  ArtworkGetDetailResponse findArtworkDetailByToken(String token);

  List<Artwork> findArtworkRecommend();

}