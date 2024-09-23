package com.hiddenartist.backend.domain.search.service;

import com.hiddenartist.backend.domain.artist.persistence.Artist;
import com.hiddenartist.backend.domain.artwork.persistence.Artwork;
import com.hiddenartist.backend.domain.exhibition.persistence.Exhibition;
import com.hiddenartist.backend.domain.search.persistence.repository.SearchRepository;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SearchService {

  private final SearchRepository searchRepository;
  private final AsyncSearchService asyncSearchService;

  @Transactional(readOnly = true)
  public void searchAllByKeyword(String keyword) {
    CompletableFuture<List<Artist>> artistSearch = asyncSearchService.searchArtistByKeyword(keyword);
    CompletableFuture<List<Artwork>> artworkSearch = asyncSearchService.searchArtworkByKeyword(keyword);
    CompletableFuture<List<Exhibition>> exhibitionSearch = asyncSearchService.searchExhibitionByKeyword(keyword);

    CompletableFuture.allOf(artistSearch, artworkSearch, exhibitionSearch).join();

    List<Artist> artists = artistSearch.join();
    List<Artwork> artworks = artworkSearch.join();
    List<Exhibition> exhibitions = exhibitionSearch.join();
    List<Artist> list = Collections.emptyList();
    // return Dto
  }

}