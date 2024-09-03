package com.sparta.newsfeed.follow.service;

import com.sparta.newsfeed.common.config.JwtUtil;
import com.sparta.newsfeed.follow.entity.Follow;
import com.sparta.newsfeed.follow.repository.FollowRepository;
import com.sparta.newsfeed.user.entity.User;
import com.sparta.newsfeed.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public void followUser(Long requesterId, HttpServletRequest request) {
        User requester = userRepository.findById(requesterId)
            .orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다"));

        Long receiverId = getUserId(request);

        User receiver = userRepository.findById(receiverId)
            .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 유저입니다"));

        if (requesterId == receiverId) {
            throw new IllegalArgumentException("본인은 팔로우할 수 없습니다.");
        }

        followRepository.save(new Follow(requester, receiver));
    }

    public Long getUserId(HttpServletRequest request) {
        String token = jwtUtil.getJwtFromHeader(request);
        Long userId = jwtUtil.getUserIdFromToken(token);
        return userId;
    }
}
