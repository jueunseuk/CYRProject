package com.junsu.cyr.repository;

import com.junsu.cyr.domain.shop.ShopCategory;
import com.junsu.cyr.domain.shop.ShopItem;
import com.junsu.cyr.domain.shop.ShopLog;
import com.junsu.cyr.domain.users.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopLogRepository extends JpaRepository<ShopLog, Long> {
    Boolean existsByUserAndShopItem(User user, ShopItem shopItem);

    List<ShopLog> findAllByUser(User user);

    List<ShopLog> findAllByUser(User user, Pageable pageable);

    @Query("select sl.shopItem.shopItemId from ShopLog as sl where sl.user = :user and sl.shopItem.shopCategory = :shopCategory and sl.shopItem.active = :active")
    List<Integer> findAllPurchaseList(User user, ShopCategory shopCategory, boolean active);

    List<ShopLog> findAllByUserAndShopItem_ShopCategory(User user, ShopCategory shopCategory, Pageable pageable);

}
