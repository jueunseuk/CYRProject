package com.junsu.cyr.model.shop;

import com.junsu.cyr.domain.shop.ShopCategory;
import com.junsu.cyr.domain.shop.ShopItem;
import com.junsu.cyr.domain.shop.ShopLog;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ShopItemResponse {
    private Integer shopItemId;
    private String name;
    private String description;
    private String imageUrl;
    private Integer price;
    private Integer saleCnt;
    private Integer shopCategoryId;
    private LocalDateTime createdAt;

    public ShopItemResponse(ShopItem shopItem) {
        this.shopItemId = shopItem.getShopItemId();
        this.name = shopItem.getName();
        this.description = shopItem.getDescription();
        this.imageUrl = shopItem.getImageUrl();
        this.price = shopItem.getPrice();
        this.saleCnt = shopItem.getSaleCnt();
        ShopCategory shopCategory = shopItem.getShopCategory();
        this.shopCategoryId = shopCategory.getShopCategoryId();
    }

    public ShopItemResponse(ShopLog shopLog) {
        this.createdAt = shopLog.getCreatedAt();
        ShopItem shopItem = shopLog.getShopItem();
        this.shopItemId = shopItem.getShopItemId();
        this.name = shopItem.getName();
        this.description = shopItem.getDescription();
        this.imageUrl = shopItem.getImageUrl();
        this.price = shopItem.getPrice();
        this.saleCnt = shopItem.getSaleCnt();
        ShopCategory shopCategory = shopItem.getShopCategory();
        this.shopCategoryId = shopCategory.getShopCategoryId();
    }
}
