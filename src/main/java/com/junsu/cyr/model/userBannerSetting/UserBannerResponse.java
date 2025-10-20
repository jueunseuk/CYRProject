package com.junsu.cyr.model.userBannerSetting;

import com.junsu.cyr.domain.shop.ShopItem;
import com.junsu.cyr.domain.users.UserBannerSetting;
import lombok.Data;

@Data
public class UserBannerResponse {
    private String imageUrl;
    private Integer sequence;
    private Boolean isActive;
    private Integer shopItemId;
    private String name;
    private String description;

    public UserBannerResponse(UserBannerSetting userBannerSetting) {
        this.sequence = userBannerSetting.getSequence();
        this.isActive = userBannerSetting.getIsActive();
        ShopItem shopItem = userBannerSetting.getShopItem();
        this.shopItemId = shopItem.getShopItemId();
        this.name = shopItem.getName();
        this.description = shopItem.getDescription();
        this.imageUrl = shopItem.getImageUrl();
    }
}
