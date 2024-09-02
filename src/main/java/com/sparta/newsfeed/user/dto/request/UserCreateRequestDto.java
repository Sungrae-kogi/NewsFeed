package com.sparta.newsfeed.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UserCreateRequestDto {
  @Email(message = "이메일 형식이 incorrect합니다")
  private String email;
  @Pattern(
      regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
      message = "비밀번호는 대소문자, 숫자, 특수문자를 포함한 8글자가 들어가야합니다")
  private String password;
  private String nickname;
  private String introduction;
}
