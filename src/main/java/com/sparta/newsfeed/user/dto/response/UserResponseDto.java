package com.sparta.newsfeed.user.dto.response;

import com.sparta.newsfeed.user.entity.User;
import lombok.Getter;

@Getter
public class UserResponseDto {
    private Long id;
    private String email;
    private String nickname;
    private String introduction;

    public UserResponseDto(String nickname, String introduction) {
        this.nickname = nickname;
        this.introduction = introduction;
    }

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.introduction = user.getIntroduction();
    }
}
