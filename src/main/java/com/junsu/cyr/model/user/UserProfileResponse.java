package com.junsu.cyr.model.user;

import com.junsu.cyr.domain.users.Gender;
import com.junsu.cyr.domain.users.Method;
import com.junsu.cyr.domain.users.Role;
import com.junsu.cyr.domain.users.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserProfileResponse {
    private Integer userId;
    private String nickname;
    private String email;
    private String profileUrl;
    private String introduction;
    private Gender gender;
    private Integer age;
    private Long expCnt;
    private Method method;
    private Integer attendanceCnt;
    private Role role;
    private LocalDateTime createdAt;

    public UserProfileResponse(User user) {
        this.userId = user.getUserId();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.profileUrl = user.getProfileUrl();
        this.introduction = user.getIntroduction();
        this.gender = user.getGender();
        this.age = user.getAge();
        this.expCnt = user.getEpxCnt();
        this.method = user.getMethod();
        this.attendanceCnt = user.getAttendanceCnt();
        this.role = user.getRole();
        this.createdAt = user.getCreatedAt();
    }
}
