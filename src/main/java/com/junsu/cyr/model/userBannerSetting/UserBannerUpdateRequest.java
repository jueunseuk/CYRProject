package com.junsu.cyr.model.userBannerSetting;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserBannerUpdateRequest {
    List<UserBannerItem> banners;
}
