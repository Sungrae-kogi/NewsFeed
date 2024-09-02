package com.sparta.newsfeed.postlike.service;

import com.sparta.newsfeed.postlike.repository.PostLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostLikeService {
    private final PostLikeRepository postLikeRepository;
}
