package com.junsu.cyr.repository;

import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.domain.users.UserBannerSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserBannerSettingRepository extends JpaRepository<UserBannerSetting, Long> {
    @Query("select max(ubs.sequence) from UserBannerSetting ubs where ubs.user = :user")
    Integer findMaxSequenceByUser(User user);

    List<UserBannerSetting> findAllByUser(User user);

    List<UserBannerSetting> findAllByUserAndIsActiveOrderBySequence(User user, boolean b);

    UserBannerSetting findByUserAndShopItem_ShopItemId(User user, Integer shopItemId);
}
