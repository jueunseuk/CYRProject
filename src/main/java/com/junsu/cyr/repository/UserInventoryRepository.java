package com.junsu.cyr.repository;

import com.junsu.cyr.domain.users.UserInventory;
import com.junsu.cyr.domain.shop.ShopItem;
import com.junsu.cyr.domain.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInventoryRepository extends JpaRepository<UserInventory, Long> {
    Optional<UserInventory> findByUserAndShopItem(User user, ShopItem shopItem);
}
