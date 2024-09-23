package com.hiddenartist.backend.domain.payment.persistence;

import com.hiddenartist.backend.domain.payment.persistence.type.PaymentStatus;
import com.hiddenartist.backend.global.converter.PaymentStatusConverter;
import com.hiddenartist.backend.global.type.BaseEntity;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment extends BaseEntity {

  private String orderId;

  private String clientOrderId;

  private Integer amount;

  private Integer cancelAmount;

  @Convert(converter = PaymentStatusConverter.class)
  private PaymentStatus paymentStatus;

}