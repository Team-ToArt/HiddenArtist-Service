package com.pop.backend.global.security.auth;

import com.pop.backend.domain.member.persistence.Member;
import com.pop.backend.domain.member.persistence.type.ProviderType;
import com.pop.backend.global.exception.type.SecurityException;
import com.pop.backend.global.exception.type.ServiceErrorCode;
import com.pop.backend.global.security.auth.OAuth2ProviderRegistry.OAuth2ProviderType;
import java.util.Map;

public record OAuth2UserAttributes(
    Map<String, Object> attributes,
    ProviderType providerType,
    String email,
    String nickname,
    String image
) {

  public static OAuth2UserAttributes of(OAuth2ProviderType providerType, Map<String, Object> attributes) {
    switch (providerType) {
      case KAKAO -> {
        return fromKakao(providerType, attributes);
      }
      case NAVER -> {
        return fromNaver(providerType, attributes);
      }
      case GOOGLE -> {
        return fromGoogle(providerType, attributes);
      }
      default -> throw new SecurityException(ServiceErrorCode.PROVIDER_NOT_FOUND);
    }
  }

  @SuppressWarnings("unchecked")
  private static OAuth2UserAttributes fromKakao
      (OAuth2ProviderType providerType, Map<String, Object> attributes) {
    Map<String, Object> kakaoAttributes = extractAttributes(providerType.ATTRIBUTES_FIELD, attributes);
    String email = (String) kakaoAttributes.get(providerType.EMAIL_FIELD);

    kakaoAttributes = (Map<String, Object>) kakaoAttributes.get("profile");
    return new OAuth2UserAttributes(
        attributes,
        ProviderType.find(providerType.name()),
        email,
        (String) kakaoAttributes.get(providerType.NICKNAME_FIELD),
        (String) kakaoAttributes.get(providerType.IMAGE_FILED)
    );
  }

  private static OAuth2UserAttributes fromNaver
      (OAuth2ProviderType providerType, Map<String, Object> attributes) {
    Map<String, Object> naverAttributes = extractAttributes(providerType.ATTRIBUTES_FIELD, attributes);
    return new OAuth2UserAttributes(
        attributes,
        ProviderType.find(providerType.name()),
        (String) naverAttributes.get(providerType.EMAIL_FIELD),
        (String) naverAttributes.get(providerType.NICKNAME_FIELD),
        (String) naverAttributes.get(providerType.IMAGE_FILED)
    );
  }

  private static OAuth2UserAttributes fromGoogle
      (OAuth2ProviderType providerType, Map<String, Object> attributes) {
    return new OAuth2UserAttributes(
        attributes,
        ProviderType.find(providerType.name()),
        (String) attributes.get(providerType.EMAIL_FIELD),
        (String) attributes.get(providerType.NICKNAME_FIELD),
        (String) attributes.get(providerType.IMAGE_FILED)
    );
  }

  @SuppressWarnings("unchecked")
  private static Map<String, Object> extractAttributes(String ATTRIBUTES_FIELD, Map<String, Object> attributes) {
    return (Map<String, Object>) attributes.get(ATTRIBUTES_FIELD);
  }

  public Member toMember(String password) {
    return Member.builder()
                 .email(email)
                 .nickname(nickname)
                 .profileImage(image)
                 .password(password)
                 .providerType(providerType)
                 .build();
  }

}