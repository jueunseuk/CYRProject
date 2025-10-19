package com.junsu.cyr.repository;

import com.junsu.cyr.domain.shop.ShopCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ShopCategoryRepository extends JpaRepository<ShopCategory, Integer> {
    ShopCategory findByShopCategoryId(Integer categoryId);
}
