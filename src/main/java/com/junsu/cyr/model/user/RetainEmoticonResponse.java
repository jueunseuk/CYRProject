package com.junsu.cyr.model.user;

import com.junsu.cyr.domain.shop.ShopItem;
import lombok.Data;

@Data
public class RetainEmoticonResponse {
    private Integer shopItemId;
    private String name;
    private String imageUrl;

    public RetainEmoticonResponse(ShopItem shopItem) {
        this.shopItemId = shopItem.getShopItemId();
        this.name = shopItem.getName();
        this.imageUrl = shopItem.getImageUrl();
    }
}
