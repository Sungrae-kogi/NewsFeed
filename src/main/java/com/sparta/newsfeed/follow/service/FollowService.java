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
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public void followUser(Long receiverId, HttpServletRequest request) {
        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_FOUND));

        Long requesterId = getUserId(request);
        User requester = getRequester(request);

        if(followRepository.findByRequesterAndReceiver(requester, receiver).isPresent()) {
            throw new ApplicationException(ErrorCode.FOLLOW_ALREADY_EXISTS);
        }

        if (requesterId.equals(receiverId)) {
            throw new ApplicationException(ErrorCode.BAD_REQUEST);
        }

        if(requester.isDeleted() || receiver.isDeleted()) {
            throw new ApplicationException(ErrorCode.USER_NOT_FOUND);
        }

        followRepository.save(new Follow(requester, receiver));
    }

    @Transactional
    public void unFollowUser(Long receiverId, HttpServletRequest request) {
        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_FOUND));

        User requester = getRequester(request);

        Follow follow = followRepository.findByRequesterAndReceiver(requester, receiver)
                .orElseThrow(() -> new ApplicationException(ErrorCode.FOLLOW_NOT_FOUND));

        followRepository.delete(follow);
    }

    public Long getUserId(HttpServletRequest request) {
        String token = jwtUtil.getJwtFromHeader(request);
        Long userId = jwtUtil.getUserIdFromToken(token);
        return userId;
    }

    private User getRequester(HttpServletRequest request) {
        Long requesterId = getUserId(request);

        User requester = userRepository.findById(requesterId)
                .orElseThrow(() -> new ApplicationException(ErrorCode.BAD_REQUEST));

        return requester;
    }
}
