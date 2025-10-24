package com.junsu.cyr.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserConditionRequest {
    private Integer page = 0;
    private Integer size = 10;
    private String sort = "nickname";
    private String direction = "ASC";
}
