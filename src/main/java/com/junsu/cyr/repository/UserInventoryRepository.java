package com.junsu.cyr.repository;

import com.junsu.cyr.domain.users.UserInventory;
import com.junsu.cyr.domain.shop.ShopItem;
import com.junsu.cyr.domain.users.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserInventoryRepository extends JpaRepository<UserInventory, Long> {
    Optional<UserInventory> findByUserAndShopItem(User user, ShopItem shopItem);

    @Query("select ui from UserInventory as ui where ui.user = :user and ui.minus > 0")
    List<UserInventory> findAllByUserWithUse(User user, Pageable pageable);

    @Query("select ui from UserInventory as ui where ui.user = :user and ui.minus < ui.plus")
    List<UserInventory> findAllByUserWithHave(User user, Pageable pageable);

    UserInventory findByUserInventoryIdAndUser(Long userInventoryId, User user);
}
