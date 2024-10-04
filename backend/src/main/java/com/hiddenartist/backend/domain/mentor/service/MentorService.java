package com.hiddenartist.backend.domain.mentor.service;

import com.hiddenartist.backend.domain.mentor.persistence.repository.MentorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MentorService {

  private final MentorRepository mentorRepository;
}