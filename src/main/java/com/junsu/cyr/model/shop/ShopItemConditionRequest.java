package com.junsu.cyr.model.shop;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopItemConditionRequest {
    private Integer page = 0;
    private Integer size = 100;
    private String sort = "shopItemId";
    private String direction = "ASC";
    private Boolean includeBoughtItems = true;
}
