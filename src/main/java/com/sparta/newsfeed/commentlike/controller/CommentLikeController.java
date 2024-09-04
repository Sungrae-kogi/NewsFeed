package com.sparta.newsfeed.commentlike.controller;

import com.sparta.newsfeed.commentlike.dto.CommentLikeRequestDto;
import com.sparta.newsfeed.commentlike.service.CommentLikeService;
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
@RequestMapping("/api/v1/commentlikes")
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/commentlikes")
    public void commentLike(HttpServletRequest request, @RequestBody CommentLikeRequestDto commentLikeRequestDto) {
        commentLikeService.commentLike(request, commentLikeRequestDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/commentlikes/{postLikeId}")
    public void deleteCommentLike(HttpServletRequest request, @PathVariable Long commentLikeId) {
        commentLikeService.deleteCommentLike(request, commentLikeId);
    }
}
