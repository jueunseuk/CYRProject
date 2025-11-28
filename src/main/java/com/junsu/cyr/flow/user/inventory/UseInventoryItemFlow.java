package com.junsu.cyr.flow.user.inventory;

import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.domain.users.UserInventory;
import com.junsu.cyr.model.userInventory.ItemUseRequest;
import com.junsu.cyr.model.userInventory.ItemUseResult;
import com.junsu.cyr.service.user.UserInventoryService;
import com.junsu.cyr.service.user.UserService;
import com.junsu.cyr.service.user.useitem.base.UseConsumableItem;
import com.junsu.cyr.service.user.useitem.factory.UseStrategyFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UseInventoryItemFlow {

    private final UserService userService;
    private final UserInventoryService userInventoryService;
    private final UseStrategyFactory useStrategyFactory;

    @Transactional
    public ItemUseResult useInventoryItem(Long userInventoryId, ItemUseRequest request, Integer userId) {
        User user = userService.getUserById(userId);
        UserInventory userInventory = userInventoryService.getUserInventoryById(userInventoryId);

        userInventory.useItem();

        String code = userInventory.getShopItem().getCode();

        UseConsumableItem strategy = useStrategyFactory.getStrategy(code);

        return strategy.use(user, request);
    }
}
