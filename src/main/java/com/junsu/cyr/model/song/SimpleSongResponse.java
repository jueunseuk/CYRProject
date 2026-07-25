package com.junsu.cyr.model.song;

import com.junsu.cyr.domain.songs.Song;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SimpleSongResponse {
    private Integer songId;
    private String title;
    private String link;
    private Integer sequence;
    private Boolean isTitle;
    private Integer albumId;
    private String albumTitle;

    public SimpleSongResponse(Song song) {
        this.songId = song.getSongId();
        this.title = song.getTitle();
        this.link = song.getLink();
        this.sequence = song.getSequence();
        this.isTitle = song.getIsTitle();
        this.albumId = song.getAlbum().getAlbumId();
        this.albumTitle = song.getAlbum().getTitle();
    }
}
