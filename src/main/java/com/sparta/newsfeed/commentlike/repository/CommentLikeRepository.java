package com.sparta.newsfeed.commentlike.repository;

import com.sparta.newsfeed.comment.entity.Comment;
import com.sparta.newsfeed.commentlike.entity.CommentLike;
import com.sparta.newsfeed.user.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

    boolean existsByUserAndComment(User user, Comment comment);

    List<CommentLike> findByComment(Comment comment);

    void deleteAllByCommentIdIn(final List<Long> commentIds);

    void deleteAllByUserId(Long id);

    void deleteAllByCommentId(final Long commentId);

}
