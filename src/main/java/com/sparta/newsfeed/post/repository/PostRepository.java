package com.sparta.newsfeed.post.repository;

import com.sparta.newsfeed.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
