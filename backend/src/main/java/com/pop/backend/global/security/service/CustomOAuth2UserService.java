package com.pop.backend.global.security.service;

import com.pop.backend.domain.user.entity.User;
import com.pop.backend.global.security.auth.OAuth2ProviderRegistry;
import com.pop.backend.global.security.auth.OAuth2ProviderRegistry.OAuth2ProviderType;
import com.pop.backend.global.security.auth.OAuth2UserAttributes;
import com.pop.backend.global.security.auth.PrincipalDetails;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

  private final AuthorizationService authorizationService;

  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    OAuth2User oAuth2User = super.loadUser(userRequest);

    String registrationId = userRequest.getClientRegistration().getRegistrationId();
    OAuth2ProviderType providerType = OAuth2ProviderRegistry.getType(registrationId);

    Map<String, Object> attributes = oAuth2User.getAttributes();

    OAuth2UserAttributes oAuth2UserAttributes = OAuth2UserAttributes.of(providerType, attributes);

    User user = authorizationService.validateUser(oAuth2UserAttributes);

    return PrincipalDetails.create(user, attributes);
  }
}
