package com.sparta.newsfeed.user.service;

import com.sparta.newsfeed.common.config.JwtUtil;
import com.sparta.newsfeed.common.config.PasswordEncoder;
import com.sparta.newsfeed.common.exception.ApplicationException;
import com.sparta.newsfeed.common.exception.ErrorCode;
import com.sparta.newsfeed.user.dto.request.UserCreateRequestDto;
import com.sparta.newsfeed.user.dto.request.UserDeleteRquestDto;
import com.sparta.newsfeed.user.dto.request.UserLoginRequestDto;
import com.sparta.newsfeed.user.dto.request.UserUpdateRequestDto;
import com.sparta.newsfeed.user.entity.User;
import com.sparta.newsfeed.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public void createUser(UserCreateRequestDto request) {
        Optional<User> existUser = userRepository.findByEmail(request.getEmail());
        if (existUser.isPresent()) {throw new ApplicationException(ErrorCode.ALREADY_USER_EXIST);}

        String password = passwordEncoder.encode(request.getPassword());
        User user = new User(request.getEmail(), password, request.getNickname(),
            request.getIntroduction());
        userRepository.save(user);
    }

    //TODO 이거부터 예외처리 하기 + FILTER부분 예외처리하기 ㅇㅅㅇ
    @Transactional(readOnly = true)
    public String loginUser(UserLoginRequestDto request) {
        User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(()->new ApplicationException(ErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ApplicationException(ErrorCode.PASSWORD_NOT_MATCH);
        }

        String token = jwtUtil.createToken(user.getId());
        return token;
    }

    @Transactional
    public void deleteUser(Long userId, UserDeleteRquestDto request) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_FOUND));

        if (user.isEnabled()) {
            throw new ApplicationException(ErrorCode.USER_NOT_FOUND);
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ApplicationException(ErrorCode.PASSWORD_NOT_MATCH);
        }

        user.delete();
    }

    @Transactional
    public void updateUser(Long userId, UserUpdateRequestDto requestDto,
        HttpServletRequest request) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_FOUND));

        String token = jwtUtil.getJwtFromHeader(request);

        if (!userId.equals(jwtUtil.getUserIdFromToken(token))) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }

        if (passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("기존 사용된 비밀번호는 사용할 수 없습니다");
        }

        String password = passwordEncoder.encode(requestDto.getPassword());

        user.update(requestDto.getNickname(), requestDto.getIntroduction(), password);
    }
}
