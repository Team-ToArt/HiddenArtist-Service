package com.hiddenartist.backend.domain.artwork.persistence.repository;

import com.hiddenartist.backend.domain.artwork.controller.response.ArtworkGetDetailResponse;

public interface CustomArtworkRepository {

  ArtworkGetDetailResponse findArtworkDetailByToken(String token);

}