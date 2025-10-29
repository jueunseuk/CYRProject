package com.junsu.cyr.model.user;

import com.junsu.cyr.domain.users.Role;
import com.junsu.cyr.domain.users.Status;
import com.junsu.cyr.domain.users.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserManagementResponse {
    private Integer userId;
    private String nickname;
    private String profileUrl;
    private Role role;
    private Status status;
    private LocalDateTime createdAt;
    private Integer warn;

    public UserManagementResponse(User user) {
        this.userId = user.getUserId();
        this.nickname = user.getNickname();
        this.profileUrl = user.getProfileUrl();
        this.role = user.getRole();
        this.status = user.getStatus();
        this.createdAt = user.getCreatedAt();
        this.warn = user.getWarn();
    }
}
