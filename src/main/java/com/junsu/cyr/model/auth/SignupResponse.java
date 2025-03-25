package com.junsu.cyr.model.auth;

import com.junsu.cyr.domain.users.Role;
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

    public SignupResponse(Integer userId, String profileUrl, String name, String nickname, LocalDateTime createdAt, Role role) {
        this.userId = userId;
        this.profileUrl = profileUrl;
        this.name = name;
        this.nickname = nickname;
        this.createdAt = createdAt;
        this.role = role;
    }
}
