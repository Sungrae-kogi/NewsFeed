package com.sparta.newsfeed.comment.dto;

import com.sparta.newsfeed.comment.entity.Comment;
import lombok.Getter;

@Getter
public class CommentResponseDto {
    private String nickname;
    private String content;
    private int likeCount;

    public CommentResponseDto(Comment comment, int likeCount){
        this.nickname = comment.getUser().getNickname();
        this.content = comment.getContent();
        this.likeCount = likeCount;
    }
}
