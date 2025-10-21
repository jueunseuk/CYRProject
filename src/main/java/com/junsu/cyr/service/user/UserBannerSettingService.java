package com.junsu.cyr.service.user;

import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.domain.users.UserBannerSetting;
import com.junsu.cyr.model.userBannerSetting.UserBannerItem;
import com.junsu.cyr.model.userBannerSetting.UserBannerResponse;
import com.junsu.cyr.model.userBannerSetting.UserBannerSettingConditionRequest;
import com.junsu.cyr.repository.UserBannerSettingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserBannerSettingService {

    private final UserBannerSettingRepository userBannerSettingRepository;
    private final UserService userService;

    public Integer findNextSequence(User user) {
        Integer next = userBannerSettingRepository.findMaxSequenceByUser(user);
        return next == null ? 1 : next+1;
    }

    public List<UserBannerResponse> getUserBannerList(UserBannerSettingConditionRequest condition, Integer userId) {
        User user = userService.getUserById(userId);

        List<UserBannerSetting> userBannerSettings;
        if(condition.getIsActive()) {
            userBannerSettings = userBannerSettingRepository.findAllByUserAndIsActiveOrderBySequence(user, true);
        } else {
            userBannerSettings = userBannerSettingRepository.findAllByUser(user);
        }
        return userBannerSettings.stream().map(UserBannerResponse::new).toList();
    }

    @Transactional
    public void updateUserBannerSequence(List<UserBannerItem> request, Integer userId) {
        User user = userService.getUserById(userId);

        for(UserBannerItem item : request) {
            UserBannerSetting userBannerSetting = userBannerSettingRepository.findByUserAndShopItem_ShopItemId(user, item.getShopItemId());
            userBannerSetting.updateSequence(item.getSequence());
            userBannerSetting.updateIsActive(item.getIsActive());
        }
    }
}
