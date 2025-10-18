package com.junsu.cyr.domain.users;

import com.junsu.cyr.domain.globals.BaseTime;
import com.junsu.cyr.domain.shop.ShopItem;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "user_inventory")
public class UserInventory extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_inventory_id")
    private Long userInventoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_item")
    private ShopItem shopItem;

    @Column(name = "plus", nullable = false)
    private Integer plus;

    @Column(name = "minus", nullable = false)
    private Integer minus;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Integer getCurrentAmount() {
        return this.plus - this.minus;
    }

    public void addItem() {
        this.plus += 1;
        this.updatedAt = LocalDateTime.now();
    }

    public void useItem() {
        this.minus += 1;
        this.updatedAt = LocalDateTime.now();
    }
}
