package com.sparta.newsfeed.follow.repository;

import com.sparta.newsfeed.follow.entity.Follow;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    List<Follow> findAllByRequesterId(final Long userId);
}
