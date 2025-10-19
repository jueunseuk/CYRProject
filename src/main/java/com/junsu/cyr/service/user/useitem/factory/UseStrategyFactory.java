package com.junsu.cyr.service.user.useitem.factory;

import com.junsu.cyr.service.user.useitem.base.UseConsumableItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class UseStrategyFactory {

    private final Map<String, UseConsumableItem> strategies;

    public UseConsumableItem getStrategy(String itemCode) {
        return strategies.get(itemCode);
    }
}
