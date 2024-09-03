package com.sparta.newsfeed.follow.controller;

import com.sparta.newsfeed.follow.service.FollowService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class FollowController {

    private final FollowService followService;

    /**
     * 팔로우를 등록합니다
     * @param userId  팔로우 대상 pathvariable로 입력받습니다.
     * @param request
     * @return HTTP status 200 OK
     */
    @PostMapping("/friends/{userId}")
    public ResponseEntity<Void> followUser(@PathVariable Long userId, HttpServletRequest request) {
        followService.followUser(userId, request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
