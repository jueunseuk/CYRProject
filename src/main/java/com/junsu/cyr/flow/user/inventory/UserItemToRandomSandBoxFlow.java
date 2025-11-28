package com.junsu.cyr.flow.user.inventory;

import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.userInventory.ItemUseRequest;
import com.junsu.cyr.model.userInventory.ItemUseResult;
import com.junsu.cyr.service.sand.SandRewardService;
import com.junsu.cyr.service.user.useitem.base.UseConsumableItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("RANDOM_SAND_BOX")
@RequiredArgsConstructor
public class UserItemToRandomSandBoxFlow implements UseConsumableItem {

    private final SandRewardService sandRewardService;

    @Override
    public ItemUseResult use(User user, ItemUseRequest request) {
        Integer randomCnt = (int) (Math.random() * 250) + 50;
        user.updateSand(randomCnt);
        sandRewardService.addSand(user, 15, randomCnt);

        return ItemUseResult.builder()
                .success(true)
                .message("success to use random sand box")
                .data(randomCnt)
                .type("RANDOM_SAND_BOX")
                .build();
    }
}
