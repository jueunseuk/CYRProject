package com.junsu.cyr.model.glass;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GlassLogRequest {
    private String sort;
    private String direction;
    private Integer size;
    private Integer page;
}
