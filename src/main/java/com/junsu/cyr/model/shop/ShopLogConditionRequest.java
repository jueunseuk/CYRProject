package com.junsu.cyr.model.shop;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopLogConditionRequest {
    private Integer page = 0;
    private Integer size = 50;
    private String sort = "shopLogId";
    private String direction = "ASC";
}
