package com.sparta.newsfeed.comment.controller;

import com.sparta.newsfeed.comment.dto.CommentRequestDto;
import com.sparta.newsfeed.comment.dto.CommentResponseDto;
import com.sparta.newsfeed.comment.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class CommentController {

    private final CommentService commentService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{postId}/comments")
    public String createComment(@PathVariable Long postId, HttpServletRequest request,
            @RequestBody CommentRequestDto commentRequestDto) {
        commentService.createComment(postId, request, commentRequestDto);
        return "댓글이 성공적으로 등록되었습니다.";
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/comments/{commentId}")
    public CommentResponseDto getComment(@PathVariable Long commentId) {
        return commentService.getComment(commentId);
    }

    // 댓글의 수정은 댓글의 작성자 혹은 게시글의 작성자만 가능. 사용자 정보, 게시글 정보
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/comments/{commentId}")
    public String updateComment(HttpServletRequest request, @PathVariable Long commentId,
            @RequestBody CommentRequestDto commentRequestDto) {
        commentService.updateComment(request, commentId, commentRequestDto);
        return "댓글이 성공적으로 수정되었습니다.";
    }

    //댓글의 삭제는 댓글의 작성자 혹은 게시글의 작성자만 가능. -> softdelete
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/comments/{commentId}")
    public void deleteComment(HttpServletRequest request, @PathVariable Long commentId) {
        commentService.deleteComment(request, commentId);
    }
}
