package com.junsu.cyr.model.achievement;

import com.junsu.cyr.domain.achievements.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AchievementLogConditionRequest {
    private Integer page = 0;
    private Integer size = 0;
    private String sort = "createdAt";
    private String direction = "ASC";
    private Type type;
}
