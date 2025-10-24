package com.junsu.cyr.service.shop;

import com.junsu.cyr.constant.MagicNumberConstant;
import com.junsu.cyr.domain.shop.Action;
import com.junsu.cyr.domain.shop.ShopCategory;
import com.junsu.cyr.domain.shop.ShopItem;
import com.junsu.cyr.domain.shop.ShopLog;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.shop.ShopLogConditionRequest;
import com.junsu.cyr.model.shop.ShopLogResponse;
import com.junsu.cyr.repository.ShopCategoryRepository;
import com.junsu.cyr.repository.ShopLogRepository;
import com.junsu.cyr.response.exception.http.BaseException;
import com.junsu.cyr.response.exception.code.ShopLogExceptionCode;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShopLogService {

    private final ShopLogRepository shopLogRepository;
    private final ShopCategoryRepository shopCategoryRepository;
    private final UserService userService;

    @Transactional
    public void createShopLog(ShopItem shopItem, User user, Action action) {
        ShopLog shopLog = ShopLog.builder()
                .shopItem(shopItem)
                .user(user)
                .consume(shopItem.getPrice())
                .action(action)
                .build();

        shopLogRepository.save(shopLog);
    }

    public List<ShopLogResponse> getAllShopLog(ShopLogConditionRequest condition, Integer userId) {
        userService.getUserById(userId);

        Sort sort = Sort.by(Sort.Direction.fromString(condition.getDirection()), condition.getSort());
        Pageable pageable = PageRequest.of(condition.getPage(), condition.getSize(), sort);
        List<ShopLog> shopLogs = shopLogRepository.findAll(pageable).getContent();
        return shopLogs.stream().map(ShopLogResponse::new).toList();
    }

    public List<ShopLogResponse> getAllShopLogByConditionWithCategory(Integer categoryId, ShopLogConditionRequest condition, Integer userId) {
        User user = userService.getUserById(userId);

        if(categoryId == MagicNumberConstant.SHOP_CATEGORY_CONSUME_TYPE) {
            throw new BaseException(ShopLogExceptionCode.CANNOT_GET_CONSUMABLE_CATEGORY_ITEM);
        }

        ShopCategory shopCategory = shopCategoryRepository.findByShopCategoryId(categoryId);

        Sort sort = Sort.by(Sort.Direction.fromString(condition.getDirection()), condition.getSort());
        Pageable pageable = PageRequest.of(condition.getPage(), condition.getSize(), sort);
        List<ShopLog> shopLogs = shopLogRepository.findAllByUserAndShopItem_ShopCategory(user, shopCategory, pageable);

        return shopLogs.stream().map(ShopLogResponse::new).toList();
    }

    public List<ShopLogResponse> getAllShopLogByConditionWithoutCategory(ShopLogConditionRequest condition, Integer userId) {
        User user = userService.getUserById(userId);
        Sort sort = Sort.by(Sort.Direction.fromString(condition.getDirection()), condition.getSort());
        Pageable pageable = PageRequest.of(condition.getPage(), condition.getSize(), sort);
        List<ShopLog> shopLogs = shopLogRepository.findAllByUser(user, pageable);

        return shopLogs.stream().map(ShopLogResponse::new).toList();
    }
}
