package com.junsu.cyr.model.song;

import com.junsu.cyr.domain.songs.Song;
import com.junsu.cyr.domain.songs.SongStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SongResponse {
    private Integer songId;
    private String title;
    private String link;
    private String imageUrl;
    private SongStatus status;
    private Integer sequence;
    private Boolean isTitle;
    private String lyrics;
    private Integer albumId;
    private String albumTitle;

    public SongResponse(Song song) {
        this.songId = song.getSongId();
        this.title = song.getTitle();
        this.link = song.getLink();
        this.imageUrl = song.getImageUrl();
        this.status = song.getStatus();
        this.sequence = song.getSequence();
        this.isTitle = song.getIsTitle();
        this.lyrics = song.getLyrics();
        this.albumId = song.getAlbum().getAlbumId();
        this.albumTitle = song.getAlbum().getTitle();
    }
}
