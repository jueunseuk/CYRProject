package com.junsu.cyr.model.user;

import com.junsu.cyr.domain.users.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserProfileUpdateRequest {
    private Integer age;
    private String nickname;
    private Gender gender;
    private String introduction;
    private String name;
}
