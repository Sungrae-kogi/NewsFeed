package com.sparta.newsfeed.post.service;

import com.sparta.newsfeed.comment.dto.response.CommentResponseDto;
import com.sparta.newsfeed.comment.entity.Comment;
import com.sparta.newsfeed.comment.repository.CommentRepository;
import com.sparta.newsfeed.common.exception.ApplicationException;
import com.sparta.newsfeed.common.exception.ErrorCode;
import com.sparta.newsfeed.post.dto.request.PostCreateRequestDto;
import com.sparta.newsfeed.post.dto.request.PostEditRequestDto;
import com.sparta.newsfeed.post.dto.response.PostResponseDto;
import com.sparta.newsfeed.post.entity.Post;
import com.sparta.newsfeed.post.repository.PostRepository;
import com.sparta.newsfeed.user.entity.User;
import com.sparta.newsfeed.user.repository.UserRepository;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public void createPost(final long userId, final PostCreateRequestDto postCreateRequestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_FOUND));

        postRepository.save(Post.of(user, postCreateRequestDto));
    }

    public PostResponseDto getPost(final long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ApplicationException(ErrorCode.POST_NOT_FOUND));

        List<Comment> comments = commentRepository.findAllByUser_Id(post.getId());
        List<CommentResponseDto> commentResponseDtos = comments.stream()
                .map(CommentResponseDto::new)
                .toList();

        return new PostResponseDto(post, commentResponseDtos);
    }

    @Transactional
    public void editPost(final long postId, @Valid PostEditRequestDto postEditRequestDto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ApplicationException(ErrorCode.POST_NOT_FOUND));

        post.changeTitle(postEditRequestDto.getTitle());
        post.changeContent(postEditRequestDto.getContent());
    }

    @Transactional
    public void deletePost(final long postId) {
        if (postRepository.existsById(postId)) {
            postRepository.deleteById(postId);
            return;
        }
        throw new ApplicationException(ErrorCode.POST_NOT_FOUND);
    }

    public List<PostResponseDto> getPosts(
            final PageRequest pageRequest,
            final String startDate,
            final String endDate
    ) {
        List<Post> posts = postRepository.findAllInDateRange(pageRequest, startDate, endDate);

        List<PostResponseDto> postResponseDtos = new ArrayList<>();
        for (Post post : posts) {
            List<CommentResponseDto> commentsResponseDtos = commentRepository.findAllByPost_Id(post.getId()).stream()
                    .map(CommentResponseDto::new)
                    .toList();
            postResponseDtos.add(new PostResponseDto(post, commentsResponseDtos));
        }

        return postResponseDtos;
    }
}
