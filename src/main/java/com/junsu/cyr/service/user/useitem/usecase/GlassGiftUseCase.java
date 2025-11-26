package com.junsu.cyr.service.user.useitem.usecase;

import com.junsu.cyr.domain.users.Status;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.userInventory.ItemUseRequest;
import com.junsu.cyr.model.userInventory.ItemUseResult;
import com.junsu.cyr.response.exception.code.UserExceptionCode;
import com.junsu.cyr.response.exception.code.UserInventoryExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import com.junsu.cyr.service.notification.usecase.GiftNotificationUseCase;
import com.junsu.cyr.service.user.UserService;
import com.junsu.cyr.service.user.useitem.base.UseConsumableItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("GLASS_GIFT_TICKET")
@RequiredArgsConstructor
public class GlassGiftUseCase implements UseConsumableItem {

    private final UserService userService;
    private final GiftNotificationUseCase giftNotificationUseCase;

    @Override
    @Transactional
    public ItemUseResult use(User user, ItemUseRequest request) {
        if(request.getOtherId() == null) {
            throw new BaseException(UserInventoryExceptionCode.INVALID_USE_REQUEST);
        }

        User target = userService.getUserById(request.getOtherId());
        if(target.getStatus() != Status.ACTIVE) {
            throw new BaseException(UserExceptionCode.NOT_EXIST_USER);
        }

        userService.addGlass(target, 6, 1);
        giftNotificationUseCase.receiveGlass(target, user);

        return ItemUseResult.builder()
                .success(true)
                .message("success to gift glass")
                .type("GLASS_GIFT_TICKET")
                .data(target.getNickname())
                .build();
    }
}
