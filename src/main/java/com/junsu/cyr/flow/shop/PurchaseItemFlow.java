package com.junsu.cyr.flow.shop;

import com.junsu.cyr.constant.MagicNumberConstant;
import com.junsu.cyr.domain.shop.Action;
import com.junsu.cyr.domain.shop.ShopItem;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.domain.users.UserInventory;
import com.junsu.cyr.repository.ShopLogRepository;
import com.junsu.cyr.repository.UserInventoryRepository;
import com.junsu.cyr.response.exception.code.ShopItemExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import com.junsu.cyr.service.experience.ExperienceRewardService;
import com.junsu.cyr.service.glass.GlassRewardService;
import com.junsu.cyr.service.shop.ShopItemService;
import com.junsu.cyr.service.shop.ShopLogService;
import com.junsu.cyr.service.user.UserBannerSettingService;
import com.junsu.cyr.service.user.UserInventoryService;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PurchaseItemFlow {

    private final UserService userService;
    private final ShopItemService shopItemService;
    private final ShopLogRepository shopLogRepository;
    private final UserInventoryRepository userInventoryRepository;
    private final UserInventoryService userInventoryService;
    private final ShopLogService shopLogService;
    private final UserBannerSettingService userBannerSettingService;
    private final ExperienceRewardService experienceRewardService;
    private final GlassRewardService glassRewardService;

    @Transactional
    public void purchaseItem(Integer shopItemId, Integer userId) {
        User user = userService.getUserById(userId);
        ShopItem shopItem = shopItemService.getShopItemById(shopItemId);

        if(shopLogRepository.existsByUserAndShopItem(user, shopItem) && !shopItem.getShopCategory().getShopCategoryId().equals(MagicNumberConstant.SHOP_CATEGORY_CONSUME_TYPE)) {
            throw new BaseException(ShopItemExceptionCode.ALREADY_PURCHASED_ITEM);
        }

        if(shopItem.getPrice() > user.getGlass()) {
            throw new BaseException(ShopItemExceptionCode.GLASSES_ARE_INSUFFICIENT);
        }

        user.useGlass(shopItem.getPrice());

        if(shopItem.getShopCategory().getShopCategoryId() == MagicNumberConstant.SHOP_CATEGORY_CONSUME_TYPE) {
            UserInventory userInventory = userInventoryRepository.findByUserAndShopItem(user, shopItem)
                    .orElse(null);

            if(userInventory == null) {
                userInventoryService.createUserInventory(user, shopItem);
                return;
            }

            userInventory.addItem();
        }

        shopLogService.createShopLog(shopItem, user, Action.PURCHASE);

        experienceRewardService.addExperience(user, 7);
        glassRewardService.addGlass(user, 3, -shopItem.getPrice());

        if(shopItem.getShopCategory().getShopCategoryId() == MagicNumberConstant.SHOP_CATEGORY_BANNER_TYPE) {
            Integer next = userBannerSettingService.findNextSequence(user);
            userBannerSettingService.createUserBannerSetting(user, shopItem, next);
        }

        shopItem.increaseSaleCnt();
    }
}
