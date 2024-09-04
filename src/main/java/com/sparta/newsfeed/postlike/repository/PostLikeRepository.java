package com.sparta.newsfeed.postlike.repository;

import com.sparta.newsfeed.post.entity.Post;
import com.sparta.newsfeed.postlike.entity.PostLike;
import com.sparta.newsfeed.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    boolean existsByUserAndPost(User user, Post post);
}
