package com.junsu.cyr.model.auth;

import com.junsu.cyr.domain.users.Gender;
import lombok.Data;

@Data
public class SignupRequest {
    private String profileUrl;
    private String name;
    private String email;
    private String password;
    private String nickname;
    private Integer age;
    private Gender gender;
}
