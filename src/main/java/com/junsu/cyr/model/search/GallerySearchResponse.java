package com.junsu.cyr.model.search;

import com.junsu.cyr.domain.gallery.Gallery;
import com.junsu.cyr.domain.gallery.GalleryImage;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class GallerySearchResponse extends SearchResponse {
    public Long galleryId;
    public String title;
    public String content;
    public Long viewCnt;
    public LocalDateTime picturedAt;
    public String thumbnailUrl;
    public Integer imageCnt;

    public GallerySearchResponse(Gallery gallery, List<GalleryImage> images, String keyword) {
        super(gallery.getUser().getUserId(), gallery.getUser().getNickname(), gallery.getCreatedAt(), "gallery");
        this.galleryId = gallery.getGalleryId();
        this.title = extractHighlight(gallery.getTitle(), keyword);
        this.content = gallery.getDescription();
        this.viewCnt = gallery.getViewCnt();
        this.picturedAt = gallery.getPicturedAt();
        this.thumbnailUrl = images.getFirst().getUrl();
        this.imageCnt = images.size();
    }

    private String extractHighlight(String text, String keyword) {
        if (text == null || keyword == null || !text.contains(keyword)) return text.substring(0, Math.min(text.length(), 50));

        int idx = text.indexOf(keyword);
        int start = Math.max(idx - 20, 0);
        int end = Math.min(idx + keyword.length() + 20, text.length());

        String snippet = text.substring(start, end);
        return snippet.replace(keyword, "<mark>" + keyword + "</mark>");
    }
}
