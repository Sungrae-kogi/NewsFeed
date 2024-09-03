package com.sparta.newsfeed.follow.service;

import com.sparta.newsfeed.common.config.JwtUtil;
import com.sparta.newsfeed.common.exception.ApplicationException;
import com.sparta.newsfeed.common.exception.ErrorCode;
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
            .orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_FOUND));

        Long receiverId = getUserId(request);
        User receiver = getReceiver(request);

        if (requesterId.equals(receiverId)) {
            throw new ApplicationException(ErrorCode.BAD_REQUEST);
        }

        followRepository.save(new Follow(requester, receiver));
    }

    public void unFollowUser(Long requesterId, HttpServletRequest request) {
        User requester = userRepository.findById(requesterId)
            .orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_FOUND));

        User receiver = getReceiver(request);

        Follow follow = followRepository.findByRequesterAndReceiver(requester, receiver)
            .orElseThrow(() -> new ApplicationException(ErrorCode.FOLLOW_NOT_FOUND));

        followRepository.delete(follow);
    }

    public Long getUserId(HttpServletRequest request) {
        String token = jwtUtil.getJwtFromHeader(request);
        Long userId = jwtUtil.getUserIdFromToken(token);
        return userId;
    }

    private User getReceiver(HttpServletRequest request) {
        Long receiverId = getUserId(request);

        User receiver = userRepository.findById(receiverId)
            .orElseThrow(() -> new ApplicationException(ErrorCode.BAD_REQUEST));

        return receiver;
    }
}
