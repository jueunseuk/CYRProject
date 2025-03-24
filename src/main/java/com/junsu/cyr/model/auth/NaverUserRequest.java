package com.junsu.cyr.model.auth;

import lombok.Data;

@Data
public class NaverUserRequest {
    private String code;
    private String state;
}
