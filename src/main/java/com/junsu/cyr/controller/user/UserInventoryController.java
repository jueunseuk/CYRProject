package com.junsu.cyr.controller.user;

import com.junsu.cyr.constant.MagicNumberConstant;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.shop.ShopLogConditionRequest;
import com.junsu.cyr.model.shop.ShopLogResponse;
import com.junsu.cyr.model.userInventory.InventoryConditionRequest;
import com.junsu.cyr.model.userInventory.InventoryConsumeItemResponse;
import com.junsu.cyr.service.shop.ShopLogService;
import com.junsu.cyr.service.user.UserInventoryService;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/inventory")
public class UserInventoryController {

    private final UserInventoryService userInventoryService;
    private final UserService userService;
    private final ShopLogService shopLogService;

    @GetMapping("/"+ MagicNumberConstant.SHOP_CATEGORY_CONSUME_TYPE)
    public ResponseEntity<List<InventoryConsumeItemResponse>> getUserInventoryConsume(@ModelAttribute InventoryConditionRequest condition, @RequestAttribute Integer userId) {
        User user = userService.getUserById(userId);
        List<InventoryConsumeItemResponse> inventoryConsumeItemResponses = userInventoryService.getAllInventoryByUser(condition, user);
        return ResponseEntity.ok(inventoryConsumeItemResponses);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<List<ShopLogResponse>> getUserInventoryByCategory(@PathVariable Integer categoryId, @ModelAttribute ShopLogConditionRequest condition, @RequestAttribute Integer userId) {
        User user = userService.getUserById(userId);
        List<ShopLogResponse> shopLogResponses = shopLogService.getAllShopLogByConditionWithCategory(categoryId, condition, user);
        return ResponseEntity.ok(shopLogResponses);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ShopLogResponse>> getUserInventoryAll(@ModelAttribute ShopLogConditionRequest condition, @RequestAttribute Integer userId) {
        User user = userService.getUserById(userId);
        List<ShopLogResponse> shopLogResponses = shopLogService.getAllShopLogByConditionWithoutCategory(condition, user);
        return ResponseEntity.ok(shopLogResponses);
    }

    @GetMapping("/use")
    public ResponseEntity<List<InventoryConsumeItemResponse>> getUserInventoryUse(@ModelAttribute InventoryConditionRequest condition, @RequestAttribute Integer userId) {
        User user = userService.getUserById(userId);
        List<InventoryConsumeItemResponse> inventoryConsumeItemResponses = userInventoryService.getAllInventoryByUserAndUse(condition, user);
        return ResponseEntity.ok(inventoryConsumeItemResponses);
    }
}
