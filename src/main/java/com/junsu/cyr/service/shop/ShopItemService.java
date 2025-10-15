package com.junsu.cyr.service.shop;

import com.junsu.cyr.domain.images.Type;
import com.junsu.cyr.domain.shop.ShopCategory;
import com.junsu.cyr.domain.shop.ShopItem;
import com.junsu.cyr.domain.users.Role;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.shop.ShopItemRequest;
import com.junsu.cyr.model.shop.ShopItemResponse;
import com.junsu.cyr.repository.ShopCategoryRepository;
import com.junsu.cyr.repository.ShopItemRepository;
import com.junsu.cyr.repository.UserRepository;
import com.junsu.cyr.response.exception.BaseException;
import com.junsu.cyr.response.exception.code.ImageExceptionCode;
import com.junsu.cyr.response.exception.code.ShopItemExceptionCode;
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

    public List<ShopItemResponse> getShopItemsByCategoryId(Integer categoryId, ShopItemRequest condition) {
        ShopCategory shopCategory = shopCategoryRepository.findAllByShopCategoryId(categoryId);

        Sort sort = Sort.by(Sort.Direction.fromString(condition.getDirection()), condition.getSort());
        Pageable pageable = PageRequest.of(condition.getPage(), condition.getSize(), sort);

        List<ShopItem> shopItems = shopItemRepository.findAllByShopCategory(shopCategory, pageable);

        return shopItems.stream().map(ShopItemResponse::new).toList();
    }
}
