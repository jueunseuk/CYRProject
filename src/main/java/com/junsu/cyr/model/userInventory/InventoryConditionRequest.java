package com.junsu.cyr.model.userInventory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryConditionRequest {
    private Integer page = 0;
    private Integer size = 50;
    private String sort = "createdAt";
    private String direction = "DESC";
}
