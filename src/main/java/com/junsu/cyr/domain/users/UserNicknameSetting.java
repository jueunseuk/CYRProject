package com.junsu.cyr.domain.users;

import com.junsu.cyr.constant.MagicNumberConstant;
import com.junsu.cyr.domain.globals.BaseTime;
import com.junsu.cyr.domain.shop.ShopItem;
import com.junsu.cyr.response.exception.code.SettingExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_nickname_setting")
public class UserNicknameSetting extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_nickname_setting_id")
    private Long userNicknameSettingId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user", nullable = false, unique = true)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shopItem", nullable = false)
    private ShopItem shopItem;

    public void updateShopItem(ShopItem shopItem) {
        if(shopItem == null || shopItem.getShopCategory().getShopCategoryId() != MagicNumberConstant.SHOP_CATEGORY_NICKNAME_TYPE) {
            throw new BaseException(SettingExceptionCode.ONLY_SAVE_NICKNAME_ITEM);
        }
        this.shopItem = shopItem;
    }
}
