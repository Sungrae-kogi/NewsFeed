package com.sparta.newsfeed.comment.service;

import com.sparta.newsfeed.comment.dto.CommentRequestDto;
import com.sparta.newsfeed.comment.dto.CommentResponseDto;
import com.sparta.newsfeed.comment.entity.Comment;
import com.sparta.newsfeed.comment.repository.CommentRepository;
import com.sparta.newsfeed.commentlike.service.CommentLikeService;
import com.sparta.newsfeed.common.config.JwtUtil;
import com.sparta.newsfeed.common.exception.ApplicationException;
import com.sparta.newsfeed.common.exception.ErrorCode;
import com.sparta.newsfeed.post.entity.Post;
import com.sparta.newsfeed.post.repository.PostRepository;
import com.sparta.newsfeed.user.entity.User;
import com.sparta.newsfeed.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentLikeService commentLikeService;
    private final JwtUtil jwtUtil;

    @Transactional
    public void createComment(Long postId, HttpServletRequest request, CommentRequestDto commentRequestDto) {
        // 댓글을 작성할 Post 검색
        Post post = findPost(postId);

        // 댓글을 작성하는 User 검색
        User user = userRepository.findById(getUserId(request))
                .orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_FOUND));

        // 댓글 저장
        commentRepository.save(new Comment(commentRequestDto, user, post));

    }

    @Transactional
    public CommentResponseDto getComment(Long commentId) {
        Comment comment = findComment(commentId);

        int likeCount = commentLikeService.getCommentLikeCount(comment.getId());

        return new CommentResponseDto(comment, likeCount);
    }

    @Transactional
    public void updateComment(HttpServletRequest request, Long commentId, CommentRequestDto commentRequestDto) {
        // 댓글은 작성자 혹은 게시물의 작성자만 수정이 가능합니다.
        User user = userRepository.findById(getUserId(request))
                .orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_FOUND));

        Comment comment = findComment(commentId);

        // 댓글 작성자의 Id와 사용자의 Id, 게시물 작성자의 Id와 비교
        if ((user.getId() == comment.getUser().getId()) || (user.getId() == comment.getPost().getId())) {
            // 수정을 요청한 유저Id가 댓글의 유저Id 또는 게시물의 유저Id와 일치한다면 -> 수정 가능
            comment.update(commentRequestDto.getContent());
        } else {
            // Id가 일치하지 않으면 -> 수정 불가
            throw new ApplicationException(ErrorCode.USER_CANNOTUPDATE_COMMENT);
        }
        commentRepository.save(comment);
    }

    @Transactional
    public void deleteComment(HttpServletRequest request, Long commentId) {
        // 댓글은 작성자 혹은 게시물의 작성자만 수정이 가능합니다.
        User user = userRepository.findById(getUserId(request))
                .orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_FOUND));

        Comment comment = findComment(commentId);

        // 댓글 작성자의 Id와 사용자의 Id, 게시물 작성자의 Id와 비교
        if ((user.getId() == comment.getUser().getId()) || (user.getId() == comment.getPost().getId())) {
            // 삭제을 요청한 유저Id가 댓글의 유저Id 또는 게시물의 유저Id와 일치한다면 -> 삭제 가능
            commentRepository.delete(comment);
        } else {
            // Id가 일치하지 않으면 -> 수정 불가
            throw new ApplicationException(ErrorCode.USER_CANNOTDELETE_COMMENT);
        }
    }

    public Comment findComment(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new ApplicationException(ErrorCode.COMMENT_NOT_FOUND));
    }

    public Post findPost(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new ApplicationException(ErrorCode.POST_NOT_FOUND));
    }

    public Long getUserId(HttpServletRequest request) {
        String token = jwtUtil.getJwtFromHeader(request);
        Long userId = jwtUtil.getUserIdFromToken(token);
        return userId;
    }
}
