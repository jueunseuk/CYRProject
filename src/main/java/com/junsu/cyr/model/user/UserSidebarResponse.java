package com.junsu.cyr.model.user;

import com.junsu.cyr.domain.users.Role;
import com.junsu.cyr.domain.users.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class UserSidebarResponse {
    private Integer userId;
    private String color;
    private Long expCnt;
    private Integer sand;
    private Integer glass;
    private Integer temperature;
    private LocalDate createdAt;
    private Role role;

    public UserSidebarResponse(User user, String color) {
        this.userId = user.getUserId();
        this.color = color;
        this.expCnt = user.getEpxCnt();
        this.sand = user.getSand();
        this.glass = user.getGlass();
        this.temperature = user.getTemperature();
        this.createdAt = user.getCreatedAt().toLocalDate();
        this.role = user.getRole();
    }
}
