package com.junsu.cyr.service.user.useitem.usecase;

import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.userInventory.ItemUseResult;
import com.junsu.cyr.service.sand.SandService;
import com.junsu.cyr.service.user.useitem.base.UseConsumableItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("RANDOM_SAND_BOX")
@RequiredArgsConstructor
public class RandomSandBoxUseCase implements UseConsumableItem {

    private final SandService sandService;

    @Override
    public ItemUseResult use(User user) {
        Integer result = sandService.openRandomSandBox(user);
        return ItemUseResult.builder()
                .success(true)
                .message("success to use random sand box")
                .data(result)
                .type("RANDOM_SAND_BOX")
                .build();
    }
}
