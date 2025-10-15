package com.junsu.cyr.model.shop;

import com.junsu.cyr.domain.shop.ShopCategory;
import com.junsu.cyr.domain.shop.ShopItem;
import lombok.Data;

@Data
public class ShopItemResponse {
    private Integer shopItemId;
    private String name;
    private String description;
    private String imageUrl;
    private Integer price;
    private Integer shopCategoryId;

    public ShopItemResponse(ShopItem shopItem) {
        this.shopItemId = shopItem.getShopItemId();
        this.name = shopItem.getName();
        this.description = shopItem.getDescription();
        this.imageUrl = shopItem.getImageUrl();
        this.price = shopItem.getPrice();
        ShopCategory shopCategory = shopItem.getShopCategory();
        this.shopCategoryId = shopCategory.getShopCategoryId();
    }
}
