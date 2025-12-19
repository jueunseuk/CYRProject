package com.junsu.cyr.model.song;

import com.junsu.cyr.domain.songs.Song;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SongResponse {
    private Integer songId;
    private String title;
    private String link;
    private String imageUrl;
    private Boolean isReleased;
    private Integer sequence;
    private Boolean representative;
    private String lyrics;

    public SongResponse(Song song) {
        this.songId = song.getSongId();
        this.title = song.getTitle();
        this.link = song.getLink();
        this.imageUrl = song.getImageUrl();
        this.isReleased = song.getIsReleased();
        this.sequence = song.getSequence();
        this.representative = song.getRepresentative();
        this.lyrics = song.getLyrics();
    }
}
