package com.junsu.cyr.model.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchConditionRequest {
    private String type;
    private String keyword;
    private Integer page = 0;
    private Integer size = 20;
    private String sort;
    private String direction = "DESC";
}
