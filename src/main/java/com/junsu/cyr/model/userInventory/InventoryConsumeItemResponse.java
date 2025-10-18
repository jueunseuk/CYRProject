package com.junsu.cyr.model.userInventory;

import com.junsu.cyr.domain.shop.ShopItem;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.domain.users.UserInventory;
import lombok.Data;

@Data
public class InventoryConsumeItemResponse {
    private Long userInventoryId;
    private Integer shopItemId;
    private String name;
    private String description;
    private String imageUrl;
    private Integer plus;
    private Integer minus;

    private Integer userId;
    private String nickname;

    public InventoryConsumeItemResponse(UserInventory userInventory) {
        this.userInventoryId = userInventory.getUserInventoryId();
        this.plus = userInventory.getPlus();
        this.minus = userInventory.getMinus();

        ShopItem shopItem = userInventory.getShopItem();
        this.shopItemId = shopItem.getShopItemId();
        this.name = shopItem.getName();
        this.description = shopItem.getDescription();
        this.imageUrl = shopItem.getImageUrl();

        User user = userInventory.getUser();
        this.userId = user.getUserId();
        this.nickname = user.getNickname();
    }
}
