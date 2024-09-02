package com.sparta.newsfeed.commentlike.controller;

import com.sparta.newsfeed.commentlike.service.CommentLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping()
public class CommentLikeController {
    private final CommentLikeService commentLikeService;
}
