package com.junsu.cyr.model.apply;

import lombok.Data;

@Data
public class ApplyConditionRequest {
    private Integer page = 0;
    private Integer size = 20;
    private String sort = "applyId";
    private String direction = "DESC";
    private Boolean confirm;
}
