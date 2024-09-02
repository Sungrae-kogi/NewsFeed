package com.sparta.newsfeed.post.controller;

import com.sparta.newsfeed.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping()
public class PostController {
    private final PostService postService;
}
