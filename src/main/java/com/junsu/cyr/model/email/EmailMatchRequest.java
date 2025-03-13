package com.junsu.cyr.model.email;

import lombok.Data;

@Data
public class EmailMatchRequest {
    private String email;
    private String code;
}
