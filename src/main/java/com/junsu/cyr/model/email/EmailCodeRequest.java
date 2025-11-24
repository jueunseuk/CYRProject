package com.junsu.cyr.model.email;

import com.junsu.cyr.domain.users.Purpose;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmailCodeRequest {
    private String email;
    private Purpose purpose;
}
