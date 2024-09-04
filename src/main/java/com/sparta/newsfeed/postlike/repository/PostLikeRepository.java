package com.sparta.newsfeed.postlike.repository;

import com.sparta.newsfeed.post.entity.Post;
import com.sparta.newsfeed.postlike.entity.PostLike;
import com.sparta.newsfeed.user.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    boolean existsByUserAndPost(User user, Post post);

    List<PostLike> findByPost(Post post);

    int countByPostId(long postId);
}
