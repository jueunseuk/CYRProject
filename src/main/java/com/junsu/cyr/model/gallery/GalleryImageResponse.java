package com.junsu.cyr.model.gallery;

import com.junsu.cyr.domain.gallery.Gallery;
import com.junsu.cyr.domain.gallery.GalleryImage;
import lombok.Data;

@Data
public class GalleryImageResponse {
    private Long galleryImageId;
    private String galleryImageUrl;
    private Long galleryId;
    private String galleryTitle;
    private Integer sequence;

    public GalleryImageResponse(GalleryImage galleryImage) {
        this.galleryImageId = galleryImage.getGalleryImageId();
        this.galleryImageUrl = galleryImage.getUrl();
        this.sequence = galleryImage.getSequence();
        Gallery gallery = galleryImage.getGallery();
        this.galleryId = gallery.getGalleryId();
        this.galleryTitle = gallery.getTitle();
    }
}
