package com.junsu.cyr.model.auth;

import com.junsu.cyr.domain.users.Method;
import lombok.Data;

@Data
public class SignupRequest {
    private Method method;
    private String profileUrl;
    private String name;
    private String email;
    private String password;
    private String nickname;
}
