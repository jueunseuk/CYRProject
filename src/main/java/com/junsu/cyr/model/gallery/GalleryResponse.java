package com.junsu.cyr.model.gallery;

import com.junsu.cyr.domain.gallery.Gallery;
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
    private LocalDateTime createdAt;
    private LocalDateTime picturedAt;
    private List<String> imageUrls;

    public GalleryResponse(Gallery gallery, List<String> imageUrls) {
        this.galleryId = gallery.getGalleryId();
        this.author = gallery.getUser().getNickname();
        this.authorId = gallery.getUser().getUserId();
        this.profileImageUrl = gallery.getUser().getProfileUrl();
        this.title = gallery.getTitle();
        this.description = gallery.getDescription();
        this.createdAt = gallery.getCreatedAt();
        this.picturedAt = gallery.getPicturedAt();
        this.imageUrls = imageUrls;
    }
}
