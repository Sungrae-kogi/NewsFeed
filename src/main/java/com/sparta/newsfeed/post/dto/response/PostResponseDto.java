package com.sparta.newsfeed.post.dto.response;

import com.sparta.newsfeed.comment.dto.response.CommentResponseDto;
import com.sparta.newsfeed.post.entity.Post;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class PostResponseDto {

    private final long postId;
    private final String title;
    private final String nickname;
    private final String content;
    private final LocalDateTime createdAt;
    private final List<CommentResponseDto> comments = new ArrayList<>();

    public PostResponseDto(final Post post, final List<CommentResponseDto> commentResponseDtos) {
        this.postId = post.getId();
        this.title = post.getTitle();
        this.nickname = post.getUser().getNickname();
        this.content = post.getContent();
        this.comments.addAll(commentResponseDtos);
        this.createdAt = post.getCreatedAt();
    }
}
