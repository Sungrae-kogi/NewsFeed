package com.sparta.newsfeed.comment.repository;

import com.sparta.newsfeed.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
