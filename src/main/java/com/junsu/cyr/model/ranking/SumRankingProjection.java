package com.junsu.cyr.model.ranking;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SumRankingProjection {
    private Integer userId;
    private Long sum;
}
