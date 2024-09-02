package com.sparta.newsfeed.post.entity;

import com.sparta.newsfeed.comment.entity.Comment;
import com.sparta.newsfeed.postlike.entity.PostLike;
import com.sparta.newsfeed.common.Timestamped;
import com.sparta.newsfeed.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@Getter
@Table(name = "posts")
public class Post extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    private List<Comment> comments;

    private List<PostLike> likes;
}
