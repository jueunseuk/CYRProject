package com.junsu.cyr.service.user.useitem.usecase;

import com.junsu.cyr.domain.shop.Action;
import com.junsu.cyr.domain.shop.ShopItem;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.shop.ShopItemConditionRequest;
import com.junsu.cyr.model.shop.ShopItemResponse;
import com.junsu.cyr.model.userInventory.ItemUseRequest;
import com.junsu.cyr.model.userInventory.ItemUseResult;
import com.junsu.cyr.response.exception.code.UserInventoryExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import com.junsu.cyr.service.shop.ShopItemService;
import com.junsu.cyr.service.shop.ShopLogService;
import com.junsu.cyr.service.user.useitem.base.UseConsumableItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("RANDOM_EMOTICON_BOX")
@RequiredArgsConstructor
public class RandomEmoticonBoxUseCase implements UseConsumableItem {

    private final ShopItemService shopItemService;
    private final ShopLogService shopLogService;

    @Override
    @Transactional
    public ItemUseResult use(User user, ItemUseRequest request) {
        ShopItemConditionRequest condition = new ShopItemConditionRequest();
        List<ShopItemResponse> shopItemResponses = shopItemService.getShopItemsByCategoryId(1, condition, user.getUserId());

        if(shopItemResponses == null) {
            throw new BaseException(UserInventoryExceptionCode.ALREADY_RETAIN_ALL_EMOTICON);
        }

        Integer random = (int) (Math.random() * shopItemResponses.size());

        ShopItem shopItem = shopItemService.getShopItemById(random);
        shopLogService.createShopLog(shopItem, user, Action.OPEN);

        return ItemUseResult.builder()
                .success(true)
                .message("success to use random emoticon box")
                .data(shopItem.getName())
                .type("RANDOM_EMOTICON_BOX")
                .build();
    }
}
