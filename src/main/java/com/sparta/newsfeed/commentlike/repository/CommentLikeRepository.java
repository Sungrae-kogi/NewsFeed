package com.sparta.newsfeed.commentlike.repository;

import com.sparta.newsfeed.commentlike.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
}
