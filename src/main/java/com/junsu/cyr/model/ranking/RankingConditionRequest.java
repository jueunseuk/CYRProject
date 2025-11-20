package com.junsu.cyr.model.ranking;

import com.junsu.cyr.domain.rankings.Period;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RankingConditionRequest {
    private Integer page = 0;
    private Integer size = 10;
    private String sort = "priority";
    private String direction = "ASC";
    private Period period;
}
