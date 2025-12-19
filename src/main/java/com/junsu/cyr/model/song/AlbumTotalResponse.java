package com.junsu.cyr.model.song;

import com.junsu.cyr.domain.songs.Album;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class AlbumTotalResponse {
    private Integer albumId;
    private String title;
    private String imageUrl;
    private LocalDate releasedAt;
    private String introduction;
    private String publisher;
    private String agency;
    private List<SongTotalResponse> songs;

    public void saveAlbum(Album album) {
        this.albumId = album.getAlbumId();
        this.title = album.getTitle();
        this.imageUrl = album.getImageUrl();
        this.releasedAt = album.getReleasedAt();
        this.introduction = album.getIntroduction();
        this.publisher = album.getPublisher();
        this.agency = album.getAgency();
    }

    public void saveSong(List<SongTotalResponse> songs) {
        this.songs = songs;
    }
}
