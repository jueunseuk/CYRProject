package com.junsu.cyr.controller.shop;

import com.junsu.cyr.constant.MagicNumberConstant;
import com.junsu.cyr.domain.glass.Glass;
import com.junsu.cyr.domain.shop.Action;
import com.junsu.cyr.domain.shop.ShopItem;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.domain.users.UserInventory;
import com.junsu.cyr.model.shop.ShopItemConditionRequest;
import com.junsu.cyr.model.shop.ShopItemResponse;
import com.junsu.cyr.service.glass.GlassService;
import com.junsu.cyr.service.shop.ShopItemService;
import com.junsu.cyr.service.shop.ShopLogService;
import com.junsu.cyr.service.user.UserInventoryService;
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
    private final ShopLogService shopLogService;
    private final UserInventoryService userInventoryService;
    private final GlassService glassService;

    @PatchMapping("/{itemId}")
    public ResponseEntity<String> uploadImage(@PathVariable Integer itemId, MultipartFile file, @RequestAttribute Integer userId) {
        shopItemService.uploadItemImage(itemId, file, userId);
        return ResponseEntity.ok("success to upload image");
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<List<ShopItemResponse>> getShopItem(@PathVariable Integer categoryId, @ModelAttribute ShopItemConditionRequest condition, @RequestAttribute Integer userId) {
        User user = userService.getUserById(userId);
        List<ShopItemResponse> shopItemResponses = shopItemService.getShopItemsByCategoryId(categoryId, condition, user);
        return ResponseEntity.ok(shopItemResponses);
    }

    @PostMapping("/{itemId}/buy")
    public ResponseEntity<String> buyItem(@PathVariable Integer itemId, @RequestAttribute Integer userId) {
        User user = userService.getUserById(userId);

        ShopItem shopItem = shopItemService.getShopItemById(itemId);
        shopItemService.buyShopItem(shopItem, user);

        if(shopItem.getShopCategory().getShopCategoryId() == MagicNumberConstant.SHOP_CATEGORY_CONSUME_TYPE) {
            userInventoryService.addToInventory(shopItem, user);
        }
        shopLogService.createShopLog(shopItem, user, Action.PURCHASE);

        userService.addExperience(user, 7);

        Glass glass = glassService.getGlass(3);
        glassService.createGlassLog(glass, user, shopItem.getPrice());

        return ResponseEntity.ok("success to buy item");
    }

    @PostMapping("/{itemId}/use")
    public ResponseEntity<String> useItem(@PathVariable Integer itemId, @RequestAttribute Integer userId) {
        User user = userService.getUserById(userId);
        ShopItem shopItem = shopItemService.getShopItemById(itemId);
        shopItemService.useShopItem(shopItem, user);
        shopLogService.createShopLog(shopItem, user, Action.USE);
        return ResponseEntity.ok("success to use item");
    }
}
