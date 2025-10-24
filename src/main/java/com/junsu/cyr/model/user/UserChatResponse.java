package com.junsu.cyr.model.user;

import com.junsu.cyr.domain.users.Role;
import com.junsu.cyr.domain.users.User;
import lombok.Data;

@Data
public class UserChatResponse {
    private Integer userId;
    private String nickName;
    private String profileUrl;
    private Role role;

    public UserChatResponse(User user) {
        this.userId = user.getUserId();
        this.nickName = user.getNickname();
        this.profileUrl = user.getProfileUrl();
        this.role = user.getRole();
    }
}
