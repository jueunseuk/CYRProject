package com.junsu.cyr.model.user;

import com.junsu.cyr.domain.users.Method;
import com.junsu.cyr.domain.users.Role;
import com.junsu.cyr.domain.users.User;
import lombok.Data;

@Data
public class UserProfileResponse {
    private Integer userId;
    private String nickname;
    private String email;
    private String profileUrl;
    private String introduction;
    private Long expCnt;
    private Method method;
    private Integer attendanceCnt;
    private Role role;

    public UserProfileResponse(User user) {
        this.userId = user.getUserId();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.profileUrl = user.getProfileUrl();
        this.introduction = user.getIntroduction();
        this.expCnt = user.getEpxCnt();
        this.method = user.getMethod();
        this.attendanceCnt = user.getAttendanceCnt();
        this.role = user.getRole();
    }
}
