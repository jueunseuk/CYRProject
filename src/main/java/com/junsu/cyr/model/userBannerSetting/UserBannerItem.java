package com.junsu.cyr.model.userBannerSetting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBannerItem {
    private Integer shopItemId;
    private Integer sequence;
    private Boolean isActive;
}