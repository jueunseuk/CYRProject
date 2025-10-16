package com.junsu.cyr.service.shop;

import com.junsu.cyr.domain.shop.Action;
import com.junsu.cyr.domain.shop.ShopItem;
import com.junsu.cyr.domain.shop.ShopLog;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.shop.ShopLogConditionRequest;
import com.junsu.cyr.model.shop.ShopLogResponse;
import com.junsu.cyr.repository.ShopLogRepository;
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

    public List<ShopLogResponse> getAllShopLog(ShopLogConditionRequest condition) {
        Sort sort = Sort.by(Sort.Direction.fromString(condition.getDirection()), condition.getSort());
        Pageable pageable = PageRequest.of(condition.getPage(), condition.getSize(), sort);
        List<ShopLog> shopLogs = shopLogRepository.findAll(pageable).getContent();
        return shopLogs.stream().map(ShopLogResponse::new).toList();
    }

    public List<ShopLogResponse> getAllShopLogByUser(User user) {
        List<ShopLog> shopLogs = shopLogRepository.findAllByUser(user);
        return shopLogs.stream().map(ShopLogResponse::new).toList();
    }
}
