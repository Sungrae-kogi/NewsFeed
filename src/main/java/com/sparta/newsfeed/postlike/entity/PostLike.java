package com.sparta.newsfeed.postlike.entity;

import com.sparta.newsfeed.post.entity.Post;
import com.sparta.newsfeed.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "postlikes")
public class PostLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;
}
