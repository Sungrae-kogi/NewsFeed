package com.sparta.newsfeed.commentlike.repository;

import com.sparta.newsfeed.comment.entity.Comment;
import com.sparta.newsfeed.commentlike.entity.CommentLike;
import com.sparta.newsfeed.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    boolean existsByUserAndComment(User user, Comment comment);

    List<CommentLike> findByComment(Comment comment);
}
