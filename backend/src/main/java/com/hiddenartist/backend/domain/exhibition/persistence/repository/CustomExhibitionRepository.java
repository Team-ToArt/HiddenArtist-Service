package com.hiddenartist.backend.domain.exhibition.persistence.repository;

import com.hiddenartist.backend.domain.exhibition.controller.response.ExhibitionSimpleResponse;
import com.hiddenartist.backend.domain.exhibition.persistence.Exhibition;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomExhibitionRepository {

  Page<ExhibitionSimpleResponse> findAllExhibitions(Pageable pageable);

  Optional<Exhibition> findExhibitionByToken(String token);

  List<Exhibition> findCurrentExhibitions();

  List<Exhibition> findUpcomingExhibitions();

  Page<Exhibition> findPastExhibitions();

}