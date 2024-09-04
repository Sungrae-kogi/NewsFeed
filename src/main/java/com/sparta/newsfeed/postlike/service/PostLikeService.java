package com.sparta.newsfeed.postlike.service;

import com.sparta.newsfeed.comment.entity.Comment;
import com.sparta.newsfeed.commentlike.entity.CommentLike;
import com.sparta.newsfeed.commentlike.repository.CommentLikeRepository;
import com.sparta.newsfeed.common.config.JwtUtil;
import com.sparta.newsfeed.common.exception.ApplicationException;
import com.sparta.newsfeed.common.exception.ErrorCode;
import com.sparta.newsfeed.post.entity.Post;
import com.sparta.newsfeed.post.repository.PostRepository;
import com.sparta.newsfeed.postlike.dto.PostLikeRequestDto;
import com.sparta.newsfeed.postlike.entity.PostLike;
import com.sparta.newsfeed.postlike.repository.PostLikeRepository;
import com.sparta.newsfeed.user.entity.User;
import com.sparta.newsfeed.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostLikeService {
    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final CommentLikeRepository commentLikeRepository;

    @Transactional
    public void postLike(HttpServletRequest request, PostLikeRequestDto postLikeRequestDto) {
        // JWT를 사용하여 유저 검색
        User user = userRepository.findById(getUserId(request)).orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_FOUND));

        // postId로 Post 검색
        Post post = postRepository.findById(postLikeRequestDto.getPostId()).orElseThrow(() -> new ApplicationException(ErrorCode.POST_NOT_FOUND));

        // user가 본인이 작성한 post에 좋아요를 누르려고하는 상황인가?
        boolean isUserTryingToLikeOwnPost = post.getUser().getId().equals(user.getId()) ? true : false;

        // 본인의 post에 좋아요를 누르려 한다면
        if(isUserTryingToLikeOwnPost){
            throw new ApplicationException(ErrorCode.USER_CANNOTLIKE_OWNPOST);
        }else{
            // user가 post에 좋아요를 한 상태인가?
            boolean alreadyLiked = postLikeRepository.existsByUserAndPost(user, post);

            // 좋아요를 누른 상태가 아니라면
            if(!alreadyLiked) {
                // 좋아요 등록
                PostLike postLike = new PostLike(user, post);
                postLikeRepository.save(postLike);
            }else{
                throw new ApplicationException(ErrorCode.POST_ALREADY_LIKED);
            }
        }
    }

    public void deletePostLike(HttpServletRequest request, Long postLikeId) {
        // JWT를 사용하여 유저 검색
        User user = userRepository.findById(getUserId(request)).orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_FOUND));

        // postLikeId로 Post 검색
        PostLike postLike = postLikeRepository.findById(postLikeId).orElseThrow(() -> new ApplicationException(ErrorCode.POST_NOT_FOUND));

        // postLike의 유저 정보가 user와 같다면.
        if(postLike.getUser().getId().equals(user.getId())) {
            postLikeRepository.delete(postLike);
        }
    }

    public int getPostLikeCount(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ApplicationException(ErrorCode.POST_NOT_FOUND));

        List<PostLike> postLikes = postLikeRepository.findByPost(post);

        return postLikes.size();
    }

    public Long getUserId(HttpServletRequest request)
    {
        String token = jwtUtil.getJwtFromHeader(request);
        Long userId = jwtUtil.getUserIdFromToken(token);
        return userId;
    }
}
