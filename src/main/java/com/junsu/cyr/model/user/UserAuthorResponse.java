package com.junsu.cyr.model.user;

import com.junsu.cyr.domain.users.Role;
import com.junsu.cyr.domain.users.User;
import lombok.Data;

@Data
public class UserAuthorResponse {
    private Integer userId;
    private String nickname;
    private String profileUrl;
    private Role role;

    public UserAuthorResponse(User user) {
        this.userId = user.getUserId();
        this.nickname = user.getNickname();
        this.profileUrl = user.getProfileUrl();
        this.role = user.getRole();
    }
}
