package com.junsu.cyr.model.ranking;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CountRankingProjection {
    private Integer userId;
    private Long count;
}
