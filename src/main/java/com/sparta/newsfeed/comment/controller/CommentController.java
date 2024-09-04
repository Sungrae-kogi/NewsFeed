package com.sparta.newsfeed.comment.controller;

import com.sparta.newsfeed.comment.dto.CommentRequestDto;
import com.sparta.newsfeed.comment.dto.CommentResponseDto;
import com.sparta.newsfeed.comment.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
public class CommentController {
    private final CommentService commentService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{postId}")
    public CommentResponseDto createComment(@PathVariable Long postId, HttpServletRequest request, @RequestBody CommentRequestDto commentRequestDto) {
        return commentService.createComment(postId, request, commentRequestDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{commentId}")
    public CommentResponseDto getComment(@PathVariable Long commentId) {
        return commentService.getComment(commentId);
    }

    // 댓글의 수정은 댓글의 작성자 혹은 게시글의 작성자만 가능. 사용자 정보, 게시글 정보
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{commentId}")
    public CommentResponseDto updateComment(@PathVariable Long commentId, @RequestBody CommentRequestDto commentRequestDto) {
        return commentService.updateComment(commentId, commentRequestDto);
    }

    //댓글의 삭제는 댓글의 작성자 혹은 게시글의 작성자만 가능.
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
    }
}
