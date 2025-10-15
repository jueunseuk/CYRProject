package com.junsu.cyr.domain.shop;

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
@Table(name = "shop_category")
public class ShopCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shop_category_id")
    private Integer shopCategoryId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;
}
