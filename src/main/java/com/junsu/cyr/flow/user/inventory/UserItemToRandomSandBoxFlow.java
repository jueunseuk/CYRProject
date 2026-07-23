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
        int randomCnt = generateRandomSandCount();

        user.updateSand(randomCnt);
        sandRewardService.addSand(user, 15, randomCnt);

        return ItemUseResult.builder()
                .success(true)
                .message("success to use random sand box")
                .data(randomCnt)
                .type("RANDOM_SAND_BOX")
                .build();
    }

    private int generateRandomSandCount() {
        int probability = (int) (Math.random() * 100) + 1;

        if (probability <= 25) {
            return randomBetween(80, 110);
        }

        if (probability <= 70) {
            return randomBetween(111, 160);
        }

        if (probability <= 95) {
            return randomBetween(161, 220);
        }

        return randomBetween(221, 300);
    }

    private int randomBetween(int min, int max) {
        return (int) (Math.random() * (max - min + 1)) + min;
    }
}
