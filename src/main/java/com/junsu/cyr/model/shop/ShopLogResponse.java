package com.junsu.cyr.model.shop;

import com.junsu.cyr.domain.shop.ShopItem;
import com.junsu.cyr.domain.shop.ShopLog;
import com.junsu.cyr.domain.users.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ShopLogResponse {
    private Long shopLogId;
    private LocalDateTime createdAt;
    private Integer consume;
    private Integer userId;
    private String nickname;
    private Integer shopItemId;
    private String name;
    private String description;
    private String imageUrl;
    private Integer shopCategoryId;

    public ShopLogResponse(ShopLog shopLog) {
        this.shopLogId = shopLog.getShopLogId();
        this.createdAt = shopLog.getCreatedAt();
        this.consume = shopLog.getConsume();
        User user = shopLog.getUser();
        this.userId = user.getUserId();
        this.nickname = user.getNickname();
        ShopItem shopItem = shopLog.getShopItem();
        this.shopItemId = shopItem.getShopItemId();
        this.name = shopItem.getName();
        this.description = shopItem.getDescription();
        this.imageUrl = shopItem.getImageUrl();
        this.shopCategoryId = shopItem.getShopCategory().getShopCategoryId();
    }
}
