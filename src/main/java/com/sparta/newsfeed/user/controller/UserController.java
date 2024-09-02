package com.sparta.newsfeed.user.controller;

import com.sparta.newsfeed.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping()
public class UserController {
    private final UserService userService;
}
