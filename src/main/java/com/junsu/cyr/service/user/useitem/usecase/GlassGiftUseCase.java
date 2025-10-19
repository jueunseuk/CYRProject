package com.junsu.cyr.service.user.useitem.usecase;

import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.userInventory.ItemUseResult;
import com.junsu.cyr.service.user.UserService;
import com.junsu.cyr.service.user.useitem.base.UseConsumableItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("GLASS_GIFT_TICKET")
@RequiredArgsConstructor
public class GlassGiftUseCase implements UseConsumableItem {

    private final UserService userService;

    @Override
    public ItemUseResult use(User user) {
        userService.giftGlassToOtherUser(user);
        return ItemUseResult.builder()
                .success(true)
                .message("success to gift glass")
                .data(null)
                .build();
    }
}
