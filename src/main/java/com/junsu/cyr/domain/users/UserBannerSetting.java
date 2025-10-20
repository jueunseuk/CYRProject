package com.junsu.cyr.domain.users;

import com.junsu.cyr.domain.shop.ShopItem;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "user_banner_setting")
public class UserBannerSetting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_banner_setting_id")
    private Long userBannerSettingId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_item")
    private ShopItem shopItem;

    @Column(name = "sequence")
    private Integer sequence;

    @Column(name = "is_active")
    private Boolean isActive;

    public void updateSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public void updateIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
}
