package com.junsu.cyr.controller.shop;

import com.junsu.cyr.model.shop.ShopLogConditionRequest;
import com.junsu.cyr.model.shop.ShopLogResponse;
import com.junsu.cyr.service.shop.ShopLogService;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shop/log")
public class ShopLogController {

    private final ShopLogService shopLogService;
    private final UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<ShopLogResponse>> getAllShopLog(@ModelAttribute ShopLogConditionRequest condition, @RequestAttribute Integer userId) {
        List<ShopLogResponse> shopLogResponses = shopLogService.getAllShopLog(condition, userId);
        return ResponseEntity.ok(shopLogResponses);
    }

    @GetMapping
    public ResponseEntity<List<ShopLogResponse>> getAllShopLogByUser(@ModelAttribute ShopLogConditionRequest condition, @RequestAttribute Integer userId) {
        List<ShopLogResponse> shopLogResponses = shopLogService.getAllShopLogByConditionWithoutCategory(condition, userId);
        return ResponseEntity.ok(shopLogResponses);
    }
}
