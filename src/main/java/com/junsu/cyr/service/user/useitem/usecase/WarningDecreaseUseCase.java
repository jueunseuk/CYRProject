package com.junsu.cyr.service.user.useitem.usecase;

import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.userInventory.ItemUseResult;
import com.junsu.cyr.service.user.UserService;
import com.junsu.cyr.service.user.useitem.base.UseConsumableItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("WARNING_DECREASE_TICKET")
@RequiredArgsConstructor
public class WarningDecreaseUseCase implements UseConsumableItem {

    private final UserService userService;

    @Override
    public ItemUseResult use(User user) {
        userService.decreaseWarning(user);
        return ItemUseResult.builder()
                .success(true)
                .message("success to decrease warning cnt")
                .data(null)
                .type("WARNING_DECREASE_TICKET")
                .build();
    }
}
