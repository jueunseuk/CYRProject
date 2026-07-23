package com.junsu.cyr.model.song;

import com.junsu.cyr.domain.songs.Album;
import com.junsu.cyr.domain.songs.AlbumType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class AlbumTotalResponse {
    private Integer albumId;
    private String title;
    private String imageUrl;
    private String introduction;
    private AlbumType albumType;
    private LocalDateTime releasedAt;
    private List<SongTotalResponse> songs;

    public void saveAlbum(Album album) {
        this.albumId = album.getAlbumId();
        this.title = album.getTitle();
        this.imageUrl = album.getImageUrl();
        this.albumType = album.getAlbumType();
        this.introduction = album.getIntroduction();
        this.releasedAt = album.getReleasedAt();
    }

    public void saveSong(List<SongTotalResponse> songs) {
        this.songs = songs;
    }
}
