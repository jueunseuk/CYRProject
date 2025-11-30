package com.junsu.cyr.flow.user.profile;

import com.junsu.cyr.domain.shop.ShopItem;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.domain.users.UserNicknameSetting;
import com.junsu.cyr.repository.UserNicknameSettingRepository;
import com.junsu.cyr.service.shop.ShopItemService;
import com.junsu.cyr.service.user.UserNicknameSettingService;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateUserNicknameColorFlow {

    private final UserService userService;
    private final UserNicknameSettingService userNicknameSettingService;
    private final ShopItemService shopItemService;
    private final UserNicknameSettingRepository userNicknameSettingRepository;

    @Transactional
    public void updateUserNicknameColor(Integer shopItemId, Integer userId) {
        User user = userService.getUserById(userId);

        if(shopItemId == null) {
            userNicknameSettingRepository.deleteByUser(user);
            return;
        }

        ShopItem shopItem = shopItemService.getShopItemById(shopItemId);
        UserNicknameSetting userNicknameSetting = userNicknameSettingService.getUserNicknameSetting(user);

        if(userNicknameSetting == null) {
            userNicknameSettingService.createUserNicknameSetting(user, shopItem);
        } else {
            userNicknameSetting.updateShopItem(shopItem);
        }
    }
}
