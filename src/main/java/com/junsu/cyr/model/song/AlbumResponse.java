package com.junsu.cyr.model.song;

import com.junsu.cyr.domain.songs.Album;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class AlbumResponse {
    private Integer albumId;
    private String title;
    private String imageUrl;
    private LocalDate releasedAt;
    private String introduction;
    private String publisher;
    private String agency;

    public AlbumResponse(Album album) {
        this.albumId = album.getAlbumId();
        this.title = album.getTitle();
        this.imageUrl = album.getImageUrl();
        this.releasedAt = album.getReleasedAt();
        this.introduction = album.getIntroduction();
        this.publisher = album.getPublisher();
        this.agency = album.getAgency();
    }
}
