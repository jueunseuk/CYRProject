package com.junsu.cyr.model.auth;

import lombok.Data;

@Data
public class EmailLoginRequest {
    private String email;
    private String password;
}
