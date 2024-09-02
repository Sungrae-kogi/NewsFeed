package com.sparta.newsfeed.user.repository;

import com.sparta.newsfeed.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
