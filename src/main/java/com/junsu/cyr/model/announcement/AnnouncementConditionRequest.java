package com.junsu.cyr.model.announcement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementConditionRequest {
    private Integer page = 0;
    private Integer pageSize = 20;
    private String sort = "createdAt";
    private String direction = "DESC";

    private String text;
}
