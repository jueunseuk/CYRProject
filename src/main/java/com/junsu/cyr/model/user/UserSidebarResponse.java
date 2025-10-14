package com.junsu.cyr.model.user;

import com.junsu.cyr.domain.users.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class UserSidebarResponse {
    private Integer userId;
    private Long expCnt;
    private Integer sand;
    private Integer glass;
    private Integer temperature;
    private LocalDate createdAt;
    private Role role;
}
