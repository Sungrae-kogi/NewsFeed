package com.sparta.newsfeed.comment.entity;

import com.sparta.newsfeed.commentlike.entity.CommentLike;
import com.sparta.newsfeed.common.Timestamped;
import com.sparta.newsfeed.post.entity.Post;
import com.sparta.newsfeed.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@Getter
@Table(name = "comments")
public class Comment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "post_id")
    private Post post;

    private List<CommentLike> likes;
}
