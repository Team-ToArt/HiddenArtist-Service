package com.pop.backend.domain.admin.service;

import com.pop.backend.domain.admin.controller.request.AdminSignUpInfo;
import com.pop.backend.domain.user.entity.User;
import com.pop.backend.domain.user.repository.UserRepository;
import com.pop.backend.global.type.Role;
import com.pop.backend.global.utils.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminAccountService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  public void saveAdminAccount(AdminSignUpInfo signUpInfo) {
    // 관리자 계정 중복 체크 추가 예정
    User user = User.builder()
                    .email(signUpInfo.email())
                    .password(passwordEncoder.encoded(signUpInfo.password()))
                    .nickname(signUpInfo.nickname())
                    .role(Role.ADMIN)
                    .build();
    userRepository.save(user);
  }

}