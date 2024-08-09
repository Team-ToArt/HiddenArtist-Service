package com.pop.backend.domain.admin.controller;

import com.pop.backend.domain.user.entity.User;
import com.pop.backend.domain.user.repository.UserRepository;
import com.pop.backend.global.type.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class TestController {

  private final UserRepository userRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  @PostMapping("/add")
  public User addUser(@RequestBody AddUser addUser) {
    User user = User.builder()
                    .email(addUser.email)
                    .password(bCryptPasswordEncoder.encode(addUser.password))
                    .nickname(addUser.nickname)
                    .role(Role.ADMIN)
                    .build();
    return userRepository.save(user);
  }

  public record AddUser(
      String email,
      String password,
      String nickname
  ) {

  }
}