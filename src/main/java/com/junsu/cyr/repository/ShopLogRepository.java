package com.junsu.cyr.repository;

import com.junsu.cyr.domain.shop.ShopLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopLogRepository extends JpaRepository<ShopLog, Long> {
}
