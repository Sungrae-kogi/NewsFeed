package com.sparta.newsfeed.post.entity;

import com.sparta.newsfeed.common.Timestamped;
import com.sparta.newsfeed.post.dto.request.PostCreateRequestDto;
import com.sparta.newsfeed.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private Post(final String title, final String content, final User user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public static Post of(final User user, final PostCreateRequestDto postCreateRequestDto) {
        return new Post(postCreateRequestDto.getTitle(), postCreateRequestDto.getContent(), user);
    }

    public void changeTitle(final String title) {
        this.title = title;
    }

    public void changeContent(final String content) {
        this.content = content;
    }
}
