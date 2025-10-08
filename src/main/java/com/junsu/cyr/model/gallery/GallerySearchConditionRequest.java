package com.junsu.cyr.model.gallery;

import lombok.Data;

@Data
public class GallerySearchConditionRequest {
    private Integer page = 0;
    private Integer size = 30;

    private String sort = "createdAt";
    private String direction = "DESC";
}
