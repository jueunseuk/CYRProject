package com.junsu.cyr.service.user;

import com.junsu.cyr.domain.users.UserInventory;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.userInventory.InventoryConditionRequest;
import com.junsu.cyr.model.userInventory.InventoryConsumeItemResponse;
import com.junsu.cyr.model.userInventory.ItemUseRequest;
import com.junsu.cyr.model.userInventory.ItemUseResult;
import com.junsu.cyr.repository.UserInventoryRepository;
import com.junsu.cyr.response.exception.http.BaseException;
import com.junsu.cyr.response.exception.code.UserInventoryExceptionCode;
import com.junsu.cyr.service.user.useitem.base.UseConsumableItem;
import com.junsu.cyr.service.user.useitem.factory.UseStrategyFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserInventoryService {

    private final UserInventoryRepository userInventoryRepository;
    private final UseStrategyFactory useStrategyFactory;
    private final UserService userService;

    public UserInventory getUserInventoryById(Long userInventoryId) {
        return userInventoryRepository.findById(userInventoryId)
                .orElseThrow(() -> new BaseException(UserInventoryExceptionCode.NOT_FOUND_USER_INVENTORY_ITEM));
    }

    public List<InventoryConsumeItemResponse> getAllInventoryByUser(InventoryConditionRequest condition, Integer userId) {
        User user = userService.getUserById(userId);
        Sort sort = Sort.by(Sort.Direction.fromString(condition.getDirection()), condition.getSort());
        Pageable pageable = PageRequest.of(condition.getPage(), condition.getSize(), sort);
        List<UserInventory> userInventories = userInventoryRepository.findAllByUserWithHave(user, pageable);
        return userInventories.stream().map(InventoryConsumeItemResponse::new).toList();
    }

    public List<InventoryConsumeItemResponse> getAllInventoryByUserAndUse(InventoryConditionRequest condition, Integer userId) {
        User user = userService.getUserById(userId);
        Sort sort = Sort.by(Sort.Direction.fromString(condition.getDirection()), condition.getSort());
        Pageable pageable = PageRequest.of(condition.getPage(), condition.getSize(), sort);
        List<UserInventory> userInventories = userInventoryRepository.findAllByUserWithUse(user, pageable);
        return userInventories.stream().map(InventoryConsumeItemResponse::new).toList();
    }

    @Transactional
    public ItemUseResult useUserInventoryItem(Long userInventoryId, ItemUseRequest request, Integer userId) {
        User user = userService.getUserById(userId);

        UserInventory userInventory = getUserInventoryById(userInventoryId);

        if (userInventory.getCurrentAmount() < 1) {
            throw new BaseException(UserInventoryExceptionCode.INSUFFICIENT_NUMBER_OF_ITEMS);
        }

        String code = userInventory.getShopItem().getCode();

        UseConsumableItem strategy = useStrategyFactory.getStrategy(code);

        ItemUseResult itemUseResult = strategy.use(user, request);

        userInventory.useItem();
        userInventoryRepository.save(userInventory);

        return itemUseResult;
    }
}
