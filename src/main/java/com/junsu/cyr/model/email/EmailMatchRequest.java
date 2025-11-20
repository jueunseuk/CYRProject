package com.junsu.cyr.model.email;

import com.junsu.cyr.domain.users.Purpose;
import lombok.Data;

@Data
public class EmailMatchRequest {
    private String email;
    private String code;
    private Purpose purpose;
}
