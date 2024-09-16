package com.hiddenartist.backend.domain.Mentoring.persistence;

import com.hiddenartist.backend.domain.Mentoring.persistence.type.MentoringStatus;
import com.hiddenartist.backend.global.converter.MentoringStatusConverter;
import com.hiddenartist.backend.global.type.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Mentoring extends BaseEntity {

  private String name;

  @Column(columnDefinition = "mediumtext")
  private String content;

  private String durationTime;

  private Integer amount;

  private Long totalApplicationCount;

  @Convert(converter = MentoringStatusConverter.class)
  private MentoringStatus mentoringStatus;

  @ManyToOne(fetch = FetchType.LAZY)
  private Mentor mentor;

}