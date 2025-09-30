package com.junsu.cyr.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserSidebarResponse {
    private Integer userId;
    private Long expCnt;
    private Long sand;
    private Long glass;
    private Integer temperature;
}
