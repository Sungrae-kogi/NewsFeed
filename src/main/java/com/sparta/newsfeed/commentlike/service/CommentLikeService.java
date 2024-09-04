package com.sparta.newsfeed.commentlike.service;

import com.sparta.newsfeed.comment.entity.Comment;
import com.sparta.newsfeed.comment.repository.CommentRepository;
import com.sparta.newsfeed.commentlike.dto.CommentLikeRequestDto;
import com.sparta.newsfeed.commentlike.entity.CommentLike;
import com.sparta.newsfeed.commentlike.repository.CommentLikeRepository;
import com.sparta.newsfeed.common.config.JwtUtil;
import com.sparta.newsfeed.common.exception.ApplicationException;
import com.sparta.newsfeed.common.exception.ErrorCode;
import com.sparta.newsfeed.post.entity.Post;
import com.sparta.newsfeed.postlike.entity.PostLike;
import com.sparta.newsfeed.user.entity.User;
import com.sparta.newsfeed.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentLikeService {
    private final CommentLikeRepository commentLikeRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public void commentLike(HttpServletRequest request, CommentLikeRequestDto commentLikeRequestDto) {
        // JWT를 사용하여 유저 검색
        User user = userRepository.findById(getUserId(request)).orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_FOUND));

        // commentId로 Comment 검색
        Comment comment  = commentRepository.findById(commentLikeRequestDto.getCommentId()).orElseThrow(() -> new ApplicationException(ErrorCode.COMMENT_NOT_FOUND));

        // user가 comment에 좋아요를 한 상태인가?
        boolean alreadyLiked = commentLikeRepository.existsByUserAndComment(user, comment);

        // 좋아요를 누른 상태가 아니라면
        if(!alreadyLiked) {
            // 좋아요 등록
            CommentLike commentLike = new CommentLike(user, comment);
            commentLikeRepository.save(commentLike);
        }else{
            throw new ApplicationException(ErrorCode.COMMENT_ALREADY_LIKED);
        }
    }

    public void deleteCommentLike(HttpServletRequest request, Long commentLikeId) {
        // JWT를 사용하여 유저 검색
        User user = userRepository.findById(getUserId(request)).orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_FOUND));

        // commentLikeId로 Comment 검색
        CommentLike commentLike = commentLikeRepository.findById(commentLikeId).orElseThrow(() -> new ApplicationException(ErrorCode.COMMENT_NOT_FOUND));

        // commentLike의 유저 정보가 user와 같다면.
        if(commentLike.getUser().getId().equals(user.getId())) {
            commentLikeRepository.delete(commentLike);
        }
    }

    public Long getUserId(HttpServletRequest request)
    {
        String token = jwtUtil.getJwtFromHeader(request);
        Long userId = jwtUtil.getUserIdFromToken(token);
        return userId;
    }
}
