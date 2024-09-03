package com.sparta.newsfeed.comment.service;

import com.sparta.newsfeed.comment.dto.CommentRequestDto;
import com.sparta.newsfeed.comment.dto.CommentResponseDto;
import com.sparta.newsfeed.comment.entity.Comment;
import com.sparta.newsfeed.comment.repository.CommentRepository;
import com.sparta.newsfeed.common.exception.ApplicationException;
import com.sparta.newsfeed.common.exception.ErrorCode;
import com.sparta.newsfeed.post.entity.Post;
import com.sparta.newsfeed.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional
    public CommentResponseDto createComment(Long postId, CommentRequestDto commentRequestDto) {
        // 댓글을 작성할 Post 검색
        Post post = findPost(postId);

        Comment savedComment = new Comment(commentRequestDto,post);

    }

    @Transactional
    public CommentResponseDto getComment(Long commentId) {
        Comment comment = findComment(commentId);
        return new CommentResponseDto(comment);
    }

    @Transactional
    public CommentResponseDto updateComment(Long commentId, CommentRequestDto commentRequestDto) {
        Comment comment = findComment(commentId);

        comment.update(commentRequestDto.getContent());

        return new CommentResponseDto(commentRepository.save(comment));
    }

    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment = findComment(commentId);
    }

    public Comment findComment(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() -> new ApplicationException(ErrorCode.COMMENT_NOT_FOUND));
    }

    public Post findPost(Long postId){
        return postRepository.findById(postId).orElseThrow(() -> new ApplicationException(ErrorCode.POST_NOT_FOUND));
    }

}
