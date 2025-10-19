package com.junsu.cyr.model.userInventory;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ItemUseResult {
    private final boolean success;
    private final String message;
    private final Object data;
}
