package com.junsu.cyr.model.glass;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GlassLogRequest {
    private String sort = "createdAt";
    private String direction = "ASC";
    private Integer size = 0;
    private Integer page = 0;
}
