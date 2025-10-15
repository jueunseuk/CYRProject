package com.junsu.cyr.repository;

import com.junsu.cyr.domain.shop.ShopCategory;
import com.junsu.cyr.domain.shop.ShopItem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShopItemRepository extends JpaRepository<ShopItem, Integer> {
    Optional<ShopItem> findByShopItemId(Integer itemId);

    List<ShopItem> findAllByShopCategoryAndActive(ShopCategory shopCategory, Boolean active, Pageable pageable);
}
