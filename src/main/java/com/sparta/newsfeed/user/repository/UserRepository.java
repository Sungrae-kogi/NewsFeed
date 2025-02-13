package com.sparta.newsfeed.user.repository;

import com.sparta.newsfeed.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(final String subject);

    Optional<User> findByIdAndIsDeletedIsFalse(final long userId);
}
