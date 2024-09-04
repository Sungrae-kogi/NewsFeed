package com.sparta.newsfeed.commentlike.repository;

import com.sparta.newsfeed.comment.entity.Comment;
import com.sparta.newsfeed.commentlike.entity.CommentLike;
import com.sparta.newsfeed.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    boolean existsByUserAndComment(User user, Comment comment);
}
