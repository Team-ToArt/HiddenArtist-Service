package com.hiddenartist.backend.domain.mentoring.persistence.repository;

import com.hiddenartist.backend.domain.mentoring.persistence.Mentoring;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomMentoringRepository {

  Page<Mentoring> findAllMentorings(Pageable pageable);

}