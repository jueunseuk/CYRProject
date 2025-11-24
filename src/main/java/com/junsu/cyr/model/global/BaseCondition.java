package com.junsu.cyr.model.global;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseCondition {
    private Integer page;
    private Integer size;
    private String sort;
    private String direction;

    public int getPage() {
        return page == null || page < 0 ? 0 : page;
    }

    public int getSize() {
        return size == null || size <= 0 ? 20 : size;
    }

    public String getSort() {
        return sort == null ? "createdAt" : sort;
    }

    public String getDirection() {
        return direction == null ? "DESC" : direction;
    }
}
