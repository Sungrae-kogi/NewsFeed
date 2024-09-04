package com.sparta.newsfeed.user.entity;

import com.sparta.newsfeed.common.Timestamped;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    private boolean isEnabled = false;

    public User(String email, String password, String nickname, String introduction) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.introduction = introduction;
    }

    public void delete() {
        this.isEnabled = true;
    }
}
