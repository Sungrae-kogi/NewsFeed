package com.sparta.newsfeed.comment.dto.response;

import com.sparta.newsfeed.comment.entity.Comment;
import lombok.Getter;

@Getter
public class CommentResponseDto {

    private String nickname;
    private String content;

    public CommentResponseDto(Comment comment) {
        this.nickname = comment.getUser().getNickname();
        this.content = comment.getContent();
    }
}
