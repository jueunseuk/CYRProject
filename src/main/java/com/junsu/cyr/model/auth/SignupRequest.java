package com.junsu.cyr.model.auth;

import com.junsu.cyr.domain.users.Method;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
public class SignupRequest {
    private Method method;
    private String name;
    private String email;
    private String password;
    private String nickname;
    private MultipartFile profileImage;
    private Boolean authenticated;
}
