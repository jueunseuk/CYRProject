package com.junsu.cyr.service.shop;

import com.junsu.cyr.constant.MagicNumberConstant;
import com.junsu.cyr.domain.images.Type;
import com.junsu.cyr.domain.shop.ShopCategory;
import com.junsu.cyr.domain.shop.ShopItem;
import com.junsu.cyr.domain.shop.ShopLog;
import com.junsu.cyr.domain.users.Role;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.domain.users.UserInventory;
import com.junsu.cyr.model.shop.ShopItemConditionRequest;
import com.junsu.cyr.model.shop.ShopItemResponse;
import com.junsu.cyr.repository.*;
import com.junsu.cyr.response.exception.BaseException;
import com.junsu.cyr.response.exception.code.ImageExceptionCode;
import com.junsu.cyr.response.exception.code.ShopItemExceptionCode;
import com.junsu.cyr.response.exception.code.UserInventoryExceptionCode;
import com.junsu.cyr.service.image.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShopItemService {

    private final UserRepository userRepository;
    private final S3Service s3Service;
    private final ShopItemRepository shopItemRepository;
    private final ShopCategoryRepository shopCategoryRepository;
    private final ShopLogRepository shopLogRepository;
    private final UserInventoryRepository userInventoryRepository;

    @Transactional
    public void uploadItemImage(Integer itemId, MultipartFile file, Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        if(user.getRole() != Role.ADMIN) {
            throw new BaseException(ImageExceptionCode.DO_NOT_HAVE_PERMISSION_TO_UPLOAD);
        }

        ShopItem shopItem = shopItemRepository.findByShopItemId(itemId)
                .orElseThrow(() -> new BaseException(ShopItemExceptionCode.NOT_FOUND_SHOP_ITEM));

        if(file.isEmpty()) {
            throw new BaseException(ImageExceptionCode.NO_PHOTOS_TO_UPLOAD);
        }

        String itemUrl;
        try {
            itemUrl = s3Service.uploadFile(file, Type.SHOP);
        } catch (Exception e) {
            throw new BaseException(ImageExceptionCode.FAILED_TO_UPLOAD_IMAGE);
        }

        shopItem.updateImageUrl(itemUrl);
    }

    public List<ShopItemResponse> getShopItemsByCategoryId(Integer categoryId, ShopItemConditionRequest condition, User user) {
        ShopCategory shopCategory = shopCategoryRepository.findByShopCategoryId(categoryId);

        Sort sort = Sort.by(Sort.Direction.fromString(condition.getDirection()), condition.getSort());
        Pageable pageable = PageRequest.of(condition.getPage(), condition.getSize(), sort);

        if(categoryId == MagicNumberConstant.SHOP_CATEGORY_CONSUME_TYPE || condition.getIncludeBoughtItems()) {
            List<ShopItem> shopItems = shopItemRepository.findAllByShopCategoryAndActive(shopCategory, true, pageable);
            return shopItems.stream().map(ShopItemResponse::new).toList();
        }

        List<Integer> purchasedItemIds  = shopLogRepository.findAllPurchaseList(user, shopCategory, true);

        List<ShopItem> shopItems;
        if(purchasedItemIds .isEmpty()) {
            shopItems = shopItemRepository.findAllByShopCategoryAndActive(shopCategory, true, pageable);
        } else {
            shopItems = shopItemRepository.findAllByShopItemIdNotInAndShopCategoryAndActive(purchasedItemIds , shopCategory, true, pageable);
        }

        return shopItems.stream().map(ShopItemResponse::new).toList();
    }

    public List<ShopItemResponse> getShopItemsByUser(User user) {
        List<ShopLog> shopLogs = shopLogRepository.findAllByUser(user);
        return shopLogs.stream().map(ShopItemResponse::new).toList();
    }

    @Transactional
    public void buyShopItem(ShopItem shopItem, User user) {
        if(shopLogRepository.existsByUserAndShopItem(user, shopItem)) {
            throw new BaseException(ShopItemExceptionCode.ALREADY_PURCHASED_ITEM);
        }

        if(shopItem.getPrice() > user.getGlass()) {
            throw new BaseException(ShopItemExceptionCode.GLASSES_ARE_INSUFFICIENT);
        }

        user.useGlass(shopItem.getPrice());
    }

    @Transactional
    public void useShopItem(ShopItem shopItem, User user) {
        if(shopItem.getShopCategory().getShopCategoryId() != MagicNumberConstant.SHOP_CATEGORY_CONSUME_TYPE) {
            throw new BaseException(ShopItemExceptionCode.CANNOT_USABLE_ITEM);
        }

        UserInventory userInventory = userInventoryRepository.findByUserAndShopItem(user, shopItem)
                .orElseThrow(() -> new BaseException(UserInventoryExceptionCode.INSUFFICIENT_NUMBER_OF_ITEMS));

        if(userInventory.getCurrentAmount() < 1) {
            throw new BaseException(UserInventoryExceptionCode.INSUFFICIENT_NUMBER_OF_ITEMS);
        }

        userInventory.useItem();
    }

    public ShopItem getShopItemById(Integer itemId) {
        return shopItemRepository.findByShopItemId(itemId)
                .orElseThrow(() -> new BaseException(ShopItemExceptionCode.NOT_FOUND_SHOP_ITEM));
    }
}
