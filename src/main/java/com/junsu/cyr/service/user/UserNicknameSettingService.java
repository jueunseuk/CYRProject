package com.junsu.cyr.service.user;

import com.junsu.cyr.constant.MagicNumberConstant;
import com.junsu.cyr.domain.shop.ShopItem;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.domain.users.UserNicknameSetting;
import com.junsu.cyr.model.shop.ShopItemResponse;
import com.junsu.cyr.repository.UserNicknameSettingRepository;
import com.junsu.cyr.response.exception.code.SettingExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserNicknameSettingService {

    private final UserNicknameSettingRepository userNicknameSettingRepository;
    private final UserService userService;

    @Transactional
    public void createUserNicknameSetting(User user, ShopItem shopItem) {
        if(shopItem.getShopCategory().getShopCategoryId() != MagicNumberConstant.SHOP_CATEGORY_NICKNAME_TYPE) {
            throw new BaseException(SettingExceptionCode.ONLY_SAVE_NICKNAME_ITEM);
        }

        UserNicknameSetting userNicknameSetting = UserNicknameSetting.builder()
                .user(user)
                .shopItem(shopItem)
                .build();

        userNicknameSettingRepository.save(userNicknameSetting);
    }

    public UserNicknameSetting getUserNicknameSetting(User user) {
        return userNicknameSettingRepository.findByUser(user);
    }

    public ShopItemResponse getUserNicknameSetting(Integer userId) {
        User user = userService.getUserById(userId);
        UserNicknameSetting userNicknameSetting = getUserNicknameSetting(user);
        return new ShopItemResponse(userNicknameSetting.getShopItem());
    }
}
