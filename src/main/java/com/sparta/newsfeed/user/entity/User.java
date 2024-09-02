package com.sparta.newsfeed.user.entity;

import com.sparta.newsfeed.common.Timestamped;
import jakarta.persistence.*;
import lombok.Getter;

import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "users")
public class User extends Timestamped {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  private String nickname;

  @Column(nullable = false)
  private String introduction;

  @Column(nullable = false)
  private String password;

//    private List<User> friends;

  public User(String email, String password, String nickname, String introduction) {
    this.email = email;
    this.password = password;
    this.nickname = nickname;
    this.introduction = introduction;
  }
}
