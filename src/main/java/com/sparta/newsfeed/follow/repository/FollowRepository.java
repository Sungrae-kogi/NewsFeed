package com.sparta.newsfeed.follow.repository;

import com.sparta.newsfeed.follow.entity.Follow;
import com.sparta.newsfeed.user.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    Optional<Follow> findByRequesterAndReceiver(User requester, User receiver);

    List<Follow> findAllByRequesterId(final Long userId);

    void deleteAllByRequesterId(Long id);

    void deleteAllByReceiverId(Long id);
}
