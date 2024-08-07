package com.pop.backend.global.security.auth;

import com.pop.backend.domain.user.entity.User;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PrincipalDetails implements OAuth2User, UserDetails {

  private SecurityUserInfo userInfo;
  private Map<String, Object> attributes;
  private Collection<? extends GrantedAuthority> authorities;

  private PrincipalDetails(SecurityUserInfo userInfo, Collection<? extends GrantedAuthority> authorities) {
    this.userInfo = userInfo;
    this.authorities = authorities;
  }

  public static PrincipalDetails create(User user, Map<String, Object> attributes) {
    String roleKey = user.getRoleKey();
    Collection<? extends GrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority(roleKey));
    SecurityUserInfo userInfo = SecurityUserInfo.convert(user);
    return new PrincipalDetails(userInfo, attributes, authorities);
  }

  public static PrincipalDetails create(User user) {
    String roleKey = user.getRoleKey();
    Collection<? extends GrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority(roleKey));
    SecurityUserInfo userInfo = SecurityUserInfo.convert(user);
    return new PrincipalDetails(userInfo, authorities);
  }

  // OAuth2 User
  @Override
  public Map<String, Object> getAttributes() {
    return attributes;
  }

  // OAuth2 User
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  // UserDetails
  @Override
  public String getPassword() {
    return userInfo.password();
  }

  // UserDetails
  @Override
  public String getUsername() {
    return userInfo.email();
  }

  // OAuth2 User
  @Override
  public String getName() {
    return userInfo.nickname();
  }
}
