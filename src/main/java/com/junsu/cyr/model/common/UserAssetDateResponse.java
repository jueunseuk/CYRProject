package com.junsu.cyr.model.common;

import lombok.Data;

@Data
public class UserAssetDateResponse {
    private Long current;
    private Long today;
    private Long incrementByDay;
    private Long week;
    private Long incrementByWeek;
    private Long Month;
    private Long incrementByMonth;
}
