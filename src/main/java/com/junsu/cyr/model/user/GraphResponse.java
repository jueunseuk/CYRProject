package com.junsu.cyr.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class GraphResponse {
    private String id;
    private List<Point> data;

    @Data
    @AllArgsConstructor
    public static class Point {
        private LocalDate x;
        private Long y;
    }
}
