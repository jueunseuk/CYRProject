package com.junsu.cyr.controller.shop;

import com.junsu.cyr.model.shop.ShopItemRequest;
import com.junsu.cyr.model.shop.ShopItemResponse;
import com.junsu.cyr.service.shop.ShopItemService;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shop/item")
public class ShopItemController {

    private final ShopItemService shopItemService;
    private final UserService userService;

    @PatchMapping("/{itemId}")
    public ResponseEntity<String> uploadImage(@PathVariable Integer itemId, MultipartFile file, @RequestAttribute Integer userId) {
        shopItemService.uploadItemImage(itemId, file, userId);
        return ResponseEntity.ok("success to upload image");
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<List<ShopItemResponse>> getShopItem(@PathVariable Integer categoryId, @ModelAttribute ShopItemRequest condition, @RequestAttribute Integer userId) {
        userService.getUserById(userId);
        List<ShopItemResponse> shopItemResponses = shopItemService.getShopItemsByCategoryId(categoryId, condition);
        return ResponseEntity.ok(shopItemResponses);
    }
}
