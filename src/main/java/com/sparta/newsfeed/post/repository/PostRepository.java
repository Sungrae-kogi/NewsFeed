package com.sparta.newsfeed.post.repository;

import com.sparta.newsfeed.post.entity.Post;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findAllByUserIdInAndCreatedAtAfterAndCreatedAtBeforeAndIsDeletedIsFalse(
            final List<Long> userIds,
            final LocalDateTime startDate,
            final LocalDateTime endDate,
            final PageRequest pageRequest
    );

    Optional<Post> findByIdAndIsDeletedIsFalse(final Long id);

    List<Post> findAllByUserIdInAndCreatedAtAfterAndCreatedAtBeforeAndIsDeletedIsFalse(
            final List<Long> followReceiverIds,
            final LocalDateTime startDate,
            final LocalDateTime endDate
    );

    List<Post> findAllByUserIdAndIsDeletedIsFalse(Long userId);
}
