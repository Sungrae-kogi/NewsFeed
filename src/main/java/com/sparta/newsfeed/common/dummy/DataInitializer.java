package com.sparta.newsfeed.common.dummy;

import com.sparta.newsfeed.comment.entity.Comment;
import com.sparta.newsfeed.comment.repository.CommentRepository;
import com.sparta.newsfeed.commentlike.entity.CommentLike;
import com.sparta.newsfeed.commentlike.repository.CommentLikeRepository;
import com.sparta.newsfeed.common.config.PasswordEncoder;
import com.sparta.newsfeed.follow.entity.Follow;
import com.sparta.newsfeed.follow.repository.FollowRepository;
import com.sparta.newsfeed.post.dto.request.PostCreateRequestDto;
import com.sparta.newsfeed.post.entity.Post;
import com.sparta.newsfeed.post.repository.PostRepository;
import com.sparta.newsfeed.postlike.entity.PostLike;
import com.sparta.newsfeed.postlike.repository.PostLikeRepository;
import com.sparta.newsfeed.user.entity.User;
import com.sparta.newsfeed.user.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DataInitializer {

//    private final UserRepository userRepository;
//    private final FollowRepository followRepository;
//    private final CommentRepository commentRepository;
//    private final CommentLikeRepository commentLikeRepository;
//    private final PostLikeRepository postLikeRepository;
//    private final PostRepository postRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    /**
//     * 유저 10명
//     * 1번 유저 -> 2 ~ 10번 팔로우, 2번 유저 -> 3 ~ 10번 팔로우, 3번 유저 -> 4 ~ 10번 팔로우 ...
//     * 각 유저별 포스트 10개
//     */
//
//    @EventListener(ApplicationReadyEvent.class)
//    @Transactional
//    public void init() {
//        initUserRepository();
//        initFollowRepository();
//        initPostRepository();
//        initCommentRepository();
//        initCommentLikeRepository();
//        initPostLikeRepository();
//    }
//
//    @Transactional
//    void initUserRepository() {
//        List<User> users = new ArrayList<>();
//        for (int i = 1; i < 11; i++) {
//            User user = new User(
//                    "test" + i + "@test.com",
//                    passwordEncoder.encode( "Password!" + i),
//                    "testNickname" + i,
//                    "testIntroduction" + i
//            );
//            users.add(user);
//        }
//
//        userRepository.saveAll(users);
//    }
//
//    @Transactional
//    void initFollowRepository() {
//        List<User> users = userRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
//        for (int i = 0; i < 9; i++) {
//            for (int j = i + 1; j < 10; j++) {
//                followRepository.save(new Follow(users.get(i), users.get(j)));
//            }
//        }
//    }
//
//    @Transactional
//    void initPostRepository() {
//        List<Post> posts = new ArrayList<>();
//        List<User> users = userRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
//        for (int i = 0; i < 10; i++) {
//            for (int j = 0; j < 10; j++) {
//                PostCreateRequestDto postCreateRequestDto = new PostCreateRequestDto(
//                        "testTitle" + j,
//                        "testContent" + j
//                );
//                posts.add(Post.of(users.get(i), postCreateRequestDto));
//            }
//        }
//
//        postRepository.saveAll(posts);
//    }
//
//    @Transactional
//    void initCommentRepository() {
//        List<User> users = userRepository.findAll(Sort.by(Direction.ASC, "id"));
//        List<Post> posts = postRepository.findAll(Sort.by(Direction.ASC, "id"));
//        List<Comment> comments = new ArrayList<>();
//        for (int i = 0; i < posts.size(); i++) {
//            for (int j = 0; j < 10; j++) {
//                Post post = posts.get(i);
//                comments.add(new Comment("testContent" + i, users.get(j), post));
//            }
//        }
//
//        commentRepository.saveAll(comments);
//    }
//
//    @Transactional
//    void initCommentLikeRepository() {
//        List<User> users = userRepository.findAll(Sort.by(Direction.ASC, "id"));
//        List<Comment> comments = commentRepository.findAll(Sort.by(Direction.ASC, "id"));
//        List<CommentLike> commentLikes = new ArrayList<>();
//
//        for (int i = 0; i < users.size(); i++) {
//            User user = users.get(i);
//            for (int j = 0; j < comments.size(); j++) {
//                commentLikes.add(new CommentLike(user, comments.get(j)));
//            }
//        }
//
//        commentLikeRepository.saveAll(commentLikes);
//    }
//
//    @Transactional
//    void initPostLikeRepository() {
//        List<User> users = userRepository.findAll(Sort.by(Direction.ASC, "id"));
//        List<Post> posts = postRepository.findAll(Sort.by(Direction.ASC, "id"));
//        List<PostLike> postLikes = new ArrayList<>();
//
//        for (int i = 0; i < users.size(); i++) {
//            User user = users.get(i);
//            for (int j = 0; j < posts.size(); j++) {
//                postLikes.add(new PostLike(user, posts.get(j)));
//            }
//        }
//
//        postLikeRepository.saveAll(postLikes);
//    }
}
