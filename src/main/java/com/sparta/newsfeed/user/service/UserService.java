package com.sparta.newsfeed.user.service;

import com.sparta.newsfeed.common.config.JwtUtil;
import com.sparta.newsfeed.common.config.PasswordEncoder;
import com.sparta.newsfeed.user.dto.UserDeleteRquestDto;
import com.sparta.newsfeed.user.dto.UserLoginRequestDto;
import com.sparta.newsfeed.user.dto.request.UserCreateRequestDto;
import com.sparta.newsfeed.user.entity.User;
import com.sparta.newsfeed.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;

  public void createUser(UserCreateRequestDto request) {
    User existUser = userRepository.findByEmail(request.getEmail());
    if (existUser != null) {throw new IllegalArgumentException("해당 이메일이 이미 있습니다.");}

    String password = passwordEncoder.encode(request.getPassword());
    User user = new User(request.getEmail(), password, request.getNickname(),
        request.getIntroduction());
    userRepository.save(user);
  }

  public String loginUser(UserLoginRequestDto request) {
    User user = userRepository.findByEmail(request.getEmail());

    if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
      throw new IllegalArgumentException("로그인 실패");
    }

    String token = jwtUtil.createToken(user.getId());
    return token;
  }

  public String getUserId(HttpServletRequest request) {
    String token = jwtUtil.getJwtFromHeader(request);
    String userId = jwtUtil.getUserIdFromToken(token);
    return userId;
  }

  @Transactional
  public void deleteUser(Long userId, UserDeleteRquestDto request) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자 입니다"));

    if(user.isEnabled()) {
      throw new IllegalArgumentException("존재하지 않는 사용자입니다.");
    }

    if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
      throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
    }

    user.delete();
  }
}
