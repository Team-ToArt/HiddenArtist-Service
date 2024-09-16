package com.hiddenartist.backend.domain.search.service;

import com.hiddenartist.backend.domain.search.persistence.repository.SearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchService {

  private final SearchRepository searchRepository;
  
}