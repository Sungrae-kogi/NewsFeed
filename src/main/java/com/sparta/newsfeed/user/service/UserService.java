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
import java.util.Objects;
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

    @Transactional(readOnly = true)
    public String loginUser(UserLoginRequestDto request) {
        User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_FOUND));

        if (user.isEnabled()) {
            throw new ApplicationException(ErrorCode.USER_NOT_FOUND);
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ApplicationException(ErrorCode.PASSWORD_NOT_MATCH);
        }

        String token = jwtUtil.createToken(user.getId());
        return token;
    }

    @Transactional
    public void deleteUser(Long userId, UserDeleteRquestDto requestDto,
        HttpServletRequest request) {

        Long currentUserId = getUserId(request);
        if (!Objects.equals(userId, currentUserId)) {
            throw new ApplicationException(ErrorCode.USER_FORBIDDEN);
        }

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_FOUND));

        if (user.isEnabled()) {
            throw new ApplicationException(ErrorCode.USER_NOT_FOUND);
        }

        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new ApplicationException(ErrorCode.PASSWORD_NOT_MATCH);
        }

        user.delete();
    }

    @Transactional
    public void updateUser(Long userId, UserUpdateRequestDto requestDto,
        HttpServletRequest request) {

        Long currentUserId = getUserId(request);
        if (!Objects.equals(userId, currentUserId)) {
            throw new ApplicationException(ErrorCode.USER_FORBIDDEN);
        }

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_FOUND));

        Long token = getUserId(request);

        if (!userId.equals(token)) {
            throw new ApplicationException(ErrorCode.USER_FORBIDDEN);
        }

        if (passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new ApplicationException(ErrorCode.PASSWORD_SAME_OLD);
        }

        String password = passwordEncoder.encode(requestDto.getPassword());

        user.update(requestDto.getNickname(), requestDto.getIntroduction(), password);
    }

    public Long getUserId(HttpServletRequest request) {
        String token = jwtUtil.getJwtFromHeader(request);
        Long userId = jwtUtil.getUserIdFromToken(token);
        return userId;
    }
}
