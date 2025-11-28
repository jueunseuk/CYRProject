package com.junsu.cyr.model.userInventory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemUseRequest {
    private Integer otherId;
    private Long postId;
}
