package com.sparta.newsfeed.user.service;

import com.sparta.newsfeed.comment.entity.Comment;
import com.sparta.newsfeed.comment.repository.CommentRepository;
import com.sparta.newsfeed.commentlike.repository.CommentLikeRepository;
import com.sparta.newsfeed.common.config.JwtUtil;
import com.sparta.newsfeed.common.config.PasswordEncoder;
import com.sparta.newsfeed.common.exception.ApplicationException;
import com.sparta.newsfeed.common.exception.ErrorCode;
import com.sparta.newsfeed.follow.repository.FollowRepository;
import com.sparta.newsfeed.post.entity.Post;
import com.sparta.newsfeed.post.repository.PostRepository;
import com.sparta.newsfeed.postlike.repository.PostLikeRepository;
import com.sparta.newsfeed.user.dto.request.UserCreateRequestDto;
import com.sparta.newsfeed.user.dto.request.UserDeleteRquestDto;
import com.sparta.newsfeed.user.dto.request.UserLoginRequestDto;
import com.sparta.newsfeed.user.dto.request.UserUpdateRequestDto;
import com.sparta.newsfeed.user.dto.response.UserResponseDto;
import com.sparta.newsfeed.user.entity.User;
import com.sparta.newsfeed.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final FollowRepository followRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;


    @Transactional
    public void createUser(UserCreateRequestDto request) {

        String password = passwordEncoder.encode(request.getPassword());
        User user = new User(request.getEmail(), password, request.getNickname(),
                request.getIntroduction());
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public String loginUser(UserLoginRequestDto request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_FOUND));

        if (user.isDeleted()) {
            throw new ApplicationException(ErrorCode.USER_NOT_FOUND);
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ApplicationException(ErrorCode.PASSWORD_NOT_MATCH);
        }

        String token = jwtUtil.createToken(user.getId());
        return token;
    }

    @Transactional
    public void deleteUser(Long userId, UserDeleteRquestDto requestDto,
            HttpServletRequest request) {

        User user = userCheck(request, userId);

        if (user.isDeleted()) {
            throw new ApplicationException(ErrorCode.USER_NOT_FOUND);
        }

        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new ApplicationException(ErrorCode.PASSWORD_NOT_MATCH);
        }

        user.delete();
        List<Post> postList = postRepository.findAllByUserIdAndIsDeletedIsFalse(userId);
        postList.forEach(Post::delete);
        List<Comment> commentList = commentRepository.findAllByUserIdAndIsDeletedIsFalse(userId);
        commentList.forEach(Comment::delete);
        commentLikeRepository.deleteAllByUserId(user.getId());
        postLikeRepository.deleteAllByUserId(user.getId());
        followRepository.deleteAllByRequesterId(user.getId());
        followRepository.deleteAllByReceiverId(user.getId());
    }

    @Transactional
    public void updateUser(Long userId, UserUpdateRequestDto requestDto,
            HttpServletRequest request) {

        User user = userCheck(request, userId);

        if (passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new ApplicationException(ErrorCode.PASSWORD_SAME_OLD);
        }

        String password = passwordEncoder.encode(requestDto.getPassword());

        user.update(requestDto.getNickname(), requestDto.getIntroduction(), password);
    }

    //jwt에서 현재 유저의 아이디를 반환해줍니다.
    public Long getUserId(HttpServletRequest request) {
        String token = jwtUtil.getJwtFromHeader(request);
        Long userId = jwtUtil.getUserIdFromToken(token);
        return userId;
    }

    //servlet에서 받아온 user아이디와 입력받은 userid 가 동일한지, check를 하는 로직입니다.
    private User userCheck(HttpServletRequest request, Long userId) {
        Long currentUserId = getUserId(request);

        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_FOUND));

        if (currentUser.isDeleted()) {
            throw new ApplicationException(ErrorCode.USER_NOT_FOUND);
        }

        if (!userId.equals(currentUserId)) {
            throw new ApplicationException(ErrorCode.USER_FORBIDDEN);
        }

        return userRepository.findById(userId)
                .orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_FOUND));
    }

    public UserResponseDto getUser(Long userId, HttpServletRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_FOUND));

        Long currentUserId = getUserId(request);

        if (!Objects.equals(userId, currentUserId)) {
            UserResponseDto simpleUserProfile = new UserResponseDto(user.getNickname(), user.getIntroduction());
            return simpleUserProfile;
        }
        return new UserResponseDto(user);
    }
}
