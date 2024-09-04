package com.sparta.newsfeed.follow.controller;

import com.sparta.newsfeed.follow.service.FollowService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
     *
     * @param requesterId 팔로우 대상 pathvariable로 입력받습니다.
     * @param request
     * @return HTTP status 201 CREATED
     */
    @PostMapping("/friends/{requesterId}")
    public ResponseEntity<Void> followUser(@PathVariable Long requesterId,
            HttpServletRequest request) {
        followService.followUser(requesterId, request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * 팔로우를 취소합니다
     *
     * @param requesterId 팔로우를 취소할 user id를 pathvariable로 입력받습니다.
     * @param request     로그인한 유저 정보를 받아옵니다
     * @return HTTP status 204 NO_CONTENT
     */
    @DeleteMapping("/friends/{requesterId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long requesterId,
            HttpServletRequest request) {
        followService.unFollowUser(requesterId, request);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
