package com.junsu.cyr.service.user.useitem.base;

import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.userInventory.ItemUseRequest;
import com.junsu.cyr.model.userInventory.ItemUseResult;

public interface UseConsumableItem {
    ItemUseResult use(User user, ItemUseRequest request);
}
