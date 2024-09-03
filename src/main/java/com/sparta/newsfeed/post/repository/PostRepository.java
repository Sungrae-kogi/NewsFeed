package com.sparta.newsfeed.post.repository;

import com.sparta.newsfeed.post.entity.Post;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select p from Post p where p.createdAt between :startDate and :endDate")
    List<Post> findAllInDateRange(
            final PageRequest pageRequest,
            final String startDate,
            final String endDate
    );
}
