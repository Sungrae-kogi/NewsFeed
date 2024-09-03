package com.sparta.newsfeed.post.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostEditRequestDto {

    @NotEmpty(message = "제목은 필수 값입니다.")
    private String title;

    @NotEmpty(message = "내용은 필수 값입니다.")
    private String content;
}
