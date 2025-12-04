package com.junsu.cyr.model.gallery;

import lombok.Data;

@Data
public class GalleryImageRequest {
    private Integer galleryId;

    private Integer page = 0;
    private Integer size = 16;

    private String sort = "createdAt";
    private String direction = "DESC";

    private String name;
}
