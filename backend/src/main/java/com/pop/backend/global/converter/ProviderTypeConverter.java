package com.pop.backend.global.converter;

import com.pop.backend.domain.account.persistence.type.ProviderType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class ProviderTypeConverter implements AttributeConverter<ProviderType, String> {

  @Override
  public String convertToDatabaseColumn(ProviderType providerType) {
    return providerType.name();
  }

  @Override
  public ProviderType convertToEntityAttribute(String s) {
    return ProviderType.valueOf(s);
  }
}
