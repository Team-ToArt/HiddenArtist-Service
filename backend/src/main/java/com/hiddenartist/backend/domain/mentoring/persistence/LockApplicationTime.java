package com.hiddenartist.backend.domain.mentoring.persistence;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LockApplicationTime {

  private String email;

  @JsonIgnore
  private String tokenValue;

  private LocalDateTime applicationTime;

}