package com.sparta.newsfeed.comment.repository;

import com.sparta.newsfeed.comment.entity.Comment;
import java.util.List;
import java.util.Optional;

import com.sparta.newsfeed.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByPostIdAndIsDeletedIsFalse(final Long postId);

    Optional<Comment> findByIdAndIsDeletedIsFalse(final Long id);
}
