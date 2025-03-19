package com.junsu.cyr.model.auth;

import com.junsu.cyr.domain.users.Role;
import lombok.Data;

@Data
public class SignupResponse {
    private Integer userId;
    private String name;
    private String nickname;
    private String accessToken;
    private Role role;

    public SignupResponse(Integer userId, String name, String nickname, String accessToken, Role role) {
        this.userId = userId;
        this.name = name;
        this.nickname = nickname;
        this.accessToken = accessToken;
        this.role = role;
    }
}
