package com.junsu.cyr.model.song;

import com.junsu.cyr.domain.songs.Song;
import com.junsu.cyr.domain.songs.Creator;
import com.junsu.cyr.domain.songs.SongStatus;
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
    private Integer sequence;
    private Boolean isTitle;
    private SongStatus songStatus;
    private String lyrics;
    private List<Creator> songCreators;

    public void saveSong(Song song) {
        this.songId = song.getSongId();
        this.title = song.getTitle();
        this.link = song.getLink();
        this.imageUrl = song.getImageUrl();
        this.sequence = song.getSequence();
        this.isTitle = song.getIsTitle();
        this.lyrics = song.getLyrics();
        this.songStatus = song.getStatus();
    }

    public void saveSongCreator(List<Creator> songCreators) {
        this.songCreators = songCreators;
    }
}
