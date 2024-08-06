package com.pop.backend.global.security.auth;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

public abstract class OAuth2ProviderRegistry {

  private static final Map<String, OAuth2ProviderType> CACHED_PROVIDER_TYPES =
      Collections.unmodifiableMap(
          Arrays.stream(OAuth2ProviderType.values())
                .collect(Collectors.toMap(OAuth2ProviderType::getREGISTRATION_ID, Function.identity()))
      );

  public static OAuth2ProviderType getType(String registrationId) {
    // 추후 Exception 설계 시 RuntimeException 수정 예정
    return Optional.ofNullable(CACHED_PROVIDER_TYPES.get(registrationId)).orElseThrow(RuntimeException::new);
  }

  @RequiredArgsConstructor
  public enum OAuth2ProviderType {
    KAKAO("kakao", "kakao_account", "email", "nickname", "profile_image_url"),
    GOOGLE("google", null, "email", "name", "picture"),
    NAVER("naver", "response", "email", "nickname", "profile_image");

    public final String REGISTRATION_ID;
    public final String ATTRIBUTES_FIELD;
    public final String EMAIL_FIELD;
    public final String NICKNAME_FIELD;
    public final String IMAGE_FILED;

    private String getREGISTRATION_ID() {
      return REGISTRATION_ID;
    }
  }

}