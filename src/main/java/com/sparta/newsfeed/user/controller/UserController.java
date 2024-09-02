package com.sparta.newsfeed.user.controller;

import com.sparta.newsfeed.user.dto.UserCreateRequestDto;
import com.sparta.newsfeed.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {
    private final UserService userService;

    @PostMapping("/users")
    public void createUser(@Valid @RequestBody UserCreateRequestDto request) {
        userService.createUser(request);
    }
}
