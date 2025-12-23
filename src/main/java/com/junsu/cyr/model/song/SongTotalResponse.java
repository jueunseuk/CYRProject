package com.junsu.cyr.model.song;

import com.junsu.cyr.domain.songs.Song;
import com.junsu.cyr.domain.songs.SongCreator;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class SongTotalResponse {
    private Integer songId;
    private String title;
    private String link;
    private String imageUrl;
    private Boolean isReleased;
    private Integer sequence;
    private Boolean representative;
    private String lyrics;
    private List<SongCreator> songCreators;

    public void saveSong(Song song) {
        this.songId = song.getSongId();
        this.title = song.getTitle();
        this.link = song.getLink();
        this.imageUrl = song.getImageUrl();
        this.isReleased = song.getIsReleased();
        this.sequence = song.getSequence();
        this.representative = song.getRepresentative();
        this.lyrics = song.getLyrics();
    }

    public void saveSongCreator(List<SongCreator> songCreators) {
        this.songCreators = songCreators;
    }
}
