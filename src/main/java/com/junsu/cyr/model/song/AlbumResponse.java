package com.junsu.cyr.model.song;

import com.junsu.cyr.domain.songs.Album;
import com.junsu.cyr.domain.songs.AlbumType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AlbumResponse {
    private Integer albumId;
    private String title;
    private String imageUrl;
    private String introduction;
    private AlbumType albumType;
    private LocalDateTime releasedAt;
    private Integer songCnt;

    public AlbumResponse(Album album) {
        this.albumId = album.getAlbumId();
        this.title = album.getTitle();
        this.imageUrl = album.getImageUrl();
        this.introduction = album.getIntroduction();
        this.albumType = album.getAlbumType();
        this.releasedAt = album.getReleasedAt();
        this.songCnt = album.getSongCnt();
    }
}
