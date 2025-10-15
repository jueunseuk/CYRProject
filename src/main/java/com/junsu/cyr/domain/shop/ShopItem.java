package com.junsu.cyr.domain.shop;

import com.junsu.cyr.domain.globals.BaseTime;
import com.junsu.cyr.domain.images.Image;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Table(name = "shop_item")
public class ShopItem extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shop_item_id")
    private Integer shopItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_category", nullable = false)
    private ShopCategory shopCategory;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Integer price;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "active")
    private Boolean active;

    public void updateImageUrl(String imageUrl) {
        this.imageUrl = imageUrl == null ? this.imageUrl : imageUrl;
    }
}
