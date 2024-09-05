package com.hiddenartist.backend.domain.genre.persistence.repository;

import com.hiddenartist.backend.domain.genre.persistence.Genre;
import java.util.List;

public interface CustomGenreRepository {

  List<Genre> findGenreByKeyword(String keyword);
}