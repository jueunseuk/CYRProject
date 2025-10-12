package com.junsu.cyr.model.gallery;

import com.junsu.cyr.domain.gallery.Gallery;
import com.junsu.cyr.domain.gallery.GalleryImage;
import com.junsu.cyr.domain.users.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GalleryImageResponse {
    private Long galleryImageId;
    private String imageUrl;
    private Long galleryId;
    private String title;
    private Integer sequence;
    private String nickname;
    private LocalDateTime pictureAt;

    public GalleryImageResponse(GalleryImage galleryImage) {
        this.galleryImageId = galleryImage.getGalleryImageId();
        this.imageUrl = galleryImage.getUrl();
        this.sequence = galleryImage.getSequence();
        this.pictureAt = galleryImage.getPicturedAt();
        Gallery gallery = galleryImage.getGallery();
        this.galleryId = gallery.getGalleryId();
        this.title = gallery.getTitle();
        User uploader = gallery.getUser();
        this.nickname = uploader.getNickname();
    }
}
