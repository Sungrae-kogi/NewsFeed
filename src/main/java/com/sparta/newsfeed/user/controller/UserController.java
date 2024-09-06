package com.sparta.newsfeed.user.controller;

import com.sparta.newsfeed.user.dto.request.UserCreateRequestDto;
import com.sparta.newsfeed.user.dto.request.UserDeleteRquestDto;
import com.sparta.newsfeed.user.dto.request.UserLoginRequestDto;
import com.sparta.newsfeed.user.dto.request.UserUpdateRequestDto;
import com.sparta.newsfeed.user.dto.response.UserResponseDto;
import com.sparta.newsfeed.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;

    /**
     * 회원가입을 수행합니다
     * @param request email, password, nickname, introduction를 받습니다 반환타입은 없습니다
     */
    @PostMapping("/users")
    public ResponseEntity<String> createUser(@Valid @RequestBody UserCreateRequestDto request) {
        userService.createUser(request);
        return new ResponseEntity<>("유저 생성이 완료되었습니다", HttpStatus.CREATED);
    }

    /**
     * 로그인을 수행합니다.
     * @param request email, password를 받습니다
     * @return jwt token을반환하여, 추후 작업시 헤더에 넣어야 합니다.
     */
    @PostMapping("/auth/login")
    public ResponseEntity<String> loginUser(@RequestBody UserLoginRequestDto request) {
        String token = userService.loginUser(request);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    /**
     * 회원 탈퇴 기능을 수행합니다. 요구사항에따라 soft delete를 수행합니다
     *
     * @param userId  삭제할 유저 아이디를 받습니다.
     * @param request password를 입력합니다
     * @return 반환타입은 상태코드와 void입니다.
     */
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId,
            @RequestBody UserDeleteRquestDto requestDto, HttpServletRequest request) {
        userService.deleteUser(userId, requestDto, request);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 유저를 수정합니다
     *
     * @param userId     수정할 유저 아이디를 받습니다
     * @param requestDto 수정할 유저의 nickname, introduction와 password를 받습니다
     * @param request   수정할 유저가 일치하는지 확인합니다.
     * @return 반환타입은 상태코드와 void입니다.
     */
    @PutMapping("/users/{userId}")
    public ResponseEntity<String> updateUser(
            @PathVariable Long userId,
            @RequestBody UserUpdateRequestDto requestDto,
            HttpServletRequest request
    ) {
        userService.updateUser(userId, requestDto, request);
        return new ResponseEntity<>("유저가 수정되었습니다",HttpStatus.OK);
    }

    /**
     * 유저를 조회합니다
     * @param userId 조회할 유저 아이디를 받습니다
     * @param request user id와 jwt token을 받아 user id가 일치하는지 확인합니다.
     * @return 조회한 유저의 정보를 반환합니다.
     */
    @GetMapping("/users/{userId}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long userId, HttpServletRequest request) {
        UserResponseDto userResponseDto = userService.getUser(userId, request);
        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }
}
