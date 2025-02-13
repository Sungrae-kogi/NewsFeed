package com.sparta.newsfeed.postlike.controller;

import com.sparta.newsfeed.postlike.dto.PostLikeRequestDto;
import com.sparta.newsfeed.postlike.service.PostLikeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PostLikeController {

    private final PostLikeService postLikeService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/postlikes")
    public void postLike(HttpServletRequest request, @RequestBody PostLikeRequestDto postLikeRequestDto) {
        postLikeService.postLike(request, postLikeRequestDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/postlikes/{postLikeId}")
    public void deletePostLike(HttpServletRequest request, @PathVariable Long postLikeId) {
        postLikeService.deletePostLike(request, postLikeId);
    }
}
