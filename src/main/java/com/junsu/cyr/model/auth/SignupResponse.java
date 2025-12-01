package com.junsu.cyr.model.auth;

import com.junsu.cyr.domain.users.Role;
import com.junsu.cyr.domain.users.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SignupResponse {
    private Integer userId;
    private String profileUrl;
    private String name;
    private String nickname;
    private LocalDateTime createdAt;
    private Role role;
    private String color;

    public SignupResponse(Integer userId, String profileUrl, String name, String nickname, LocalDateTime createdAt, Role role, String color) {
        this.userId = userId;
        this.profileUrl = profileUrl;
        this.name = name;
        this.nickname = nickname;
        this.createdAt = createdAt;
        this.role = role;
        this.color = color;
    }

    public SignupResponse(User user, String color) {
        this.userId = user.getUserId();
        this.profileUrl = user.getProfileUrl();
        this.name = user.getName();
        this.nickname = user.getNickname();
        this.createdAt = user.getCreatedAt();
        this.role = user.getRole();
        this.color = color;
    }
}
