package com.hiddenartist.backend.domain.mentoring.persistence;

import com.hiddenartist.backend.domain.account.persistence.Account;
import com.hiddenartist.backend.domain.mentoring.persistence.type.MentoringApplicationStatus;
import com.hiddenartist.backend.domain.payment.persistence.Payment;
import com.hiddenartist.backend.global.converter.MentoringApplicationStatusConverter;
import com.hiddenartist.backend.global.type.BaseEntity;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MentoringApplication extends BaseEntity {

  private LocalDateTime applicationTime;

  @Convert(converter = MentoringApplicationStatusConverter.class)
  private MentoringApplicationStatus mentoringApplicationStatus;

  @ManyToOne(fetch = FetchType.LAZY)
  private Mentoring mentoring;

  @ManyToOne(fetch = FetchType.LAZY)
  private Account account;

  @OneToOne(fetch = FetchType.LAZY)
  private Payment payment;

}