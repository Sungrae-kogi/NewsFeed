package com.sparta.newsfeed.comment.repository;

import com.sparta.newsfeed.comment.entity.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByUser_Id(final long id);

    List<Comment> findAllByPost_Id(final Long postId);
}
