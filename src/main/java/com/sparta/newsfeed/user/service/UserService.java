package com.sparta.newsfeed.user.service;

import com.sparta.newsfeed.common.config.PasswordEncoder;
import com.sparta.newsfeed.user.dto.UserCreateRequestDto;
import com.sparta.newsfeed.user.entity.User;
import com.sparta.newsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public void createUser(UserCreateRequestDto request) {
    userRepository.findByEmail(request.getEmail())
        .ifPresent(email -> {throw new IllegalArgumentException("이미 존재하는 이메일입니다");});

    String password = passwordEncoder.encode(request.getPassword());
    User user = new User(request.getEmail(), password, request.getNickname(),
        request.getIntroduction());
    userRepository.save(user);
  }
}
