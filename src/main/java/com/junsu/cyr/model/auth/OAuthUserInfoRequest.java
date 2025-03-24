package com.junsu.cyr.model.auth;

import com.junsu.cyr.domain.users.Method;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OAuthUserInfoRequest {
    private String name;
    private String email;
    private String profileImageUrl;
    private Method method;
}
