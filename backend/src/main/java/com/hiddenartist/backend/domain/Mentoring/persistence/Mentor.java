package com.hiddenartist.backend.domain.Mentoring.persistence;

import com.hiddenartist.backend.domain.Mentoring.persistence.type.Career;
import com.hiddenartist.backend.domain.Mentoring.persistence.type.CertificationStatus;
import com.hiddenartist.backend.domain.account.persistence.Account;
import com.hiddenartist.backend.global.converter.CareerConverter;
import com.hiddenartist.backend.global.converter.CertificationStatusConverter;
import com.hiddenartist.backend.global.type.BaseEntity;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Mentor extends BaseEntity {

  private String summary;

  private String organization;

  @Convert(converter = CareerConverter.class)
  private Career career;

  private String bankName;

  private String accountName;

  private String accountNumber;

  private String contactEmail;

  @Convert(converter = CertificationStatusConverter.class)
  private CertificationStatus certificationStatus;

  @OneToOne(fetch = FetchType.LAZY)
  private Account account;

}