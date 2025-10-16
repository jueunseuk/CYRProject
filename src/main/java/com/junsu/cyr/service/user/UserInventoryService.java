package com.junsu.cyr.service.user;

import com.junsu.cyr.domain.users.UserInventory;
import com.junsu.cyr.domain.shop.ShopItem;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.repository.UserInventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserInventoryService {

    private final UserInventoryRepository userInventoryRepository;

    @Transactional
    public void addToInventory(ShopItem shopItem, User user) {
        UserInventory userInventory = userInventoryRepository.findByUserAndShopItem(user, shopItem)
                .orElse(null);

        if(userInventory == null) {
            createInventory(shopItem, user);
            return;
        }

        userInventory.addItem();
    }

    @Transactional
    public void createInventory(ShopItem shopItem, User user) {
        UserInventory userInventory = UserInventory.builder()
                .user(user)
                .shopItem(shopItem)
                .quantity(1)
                .updatedAt(LocalDateTime.now())
                .build();

        userInventoryRepository.save(userInventory);
    }
}
