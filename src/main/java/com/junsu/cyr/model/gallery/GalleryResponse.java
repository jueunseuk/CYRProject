package com.junsu.cyr.model.gallery;

import com.junsu.cyr.domain.gallery.Gallery;
import com.junsu.cyr.domain.gallery.Tag;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class GalleryResponse {
    private Long galleryId;
    private String author;
    private Integer authorId;
    private String profileImageUrl;
    private String title;
    private String description;
    private Long viewCnt;
    private LocalDateTime createdAt;
    private LocalDateTime picturedAt;
    private List<String> imageUrls;
    private List<String> tags;

    public GalleryResponse(Gallery gallery, List<String> imageUrls, List<String> tags) {
        this.galleryId = gallery.getGalleryId();
        this.author = gallery.getUser().getNickname();
        this.authorId = gallery.getUser().getUserId();
        this.profileImageUrl = gallery.getUser().getProfileUrl();
        this.title = gallery.getTitle();
        this.description = gallery.getDescription();
        this.viewCnt = gallery.getViewCnt();
        this.createdAt = gallery.getCreatedAt();
        this.picturedAt = gallery.getPicturedAt();
        this.imageUrls = imageUrls;
        this.tags = tags;
    }
}
