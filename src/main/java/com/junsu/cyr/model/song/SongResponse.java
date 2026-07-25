package com.junsu.cyr.model.song;

import com.junsu.cyr.domain.songs.Creator;
import com.junsu.cyr.domain.songs.Song;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SongResponse {
    private Integer songId;
    private String title;
    private String link;
    private String imageUrl;
    private Integer sequence;
    private Boolean isTitle;
    private String lyrics;
    private String introduction;
    private Integer albumId;
    private String albumTitle;
    private LocalDateTime releasedAt;
    private List<SongCreatorResponse> creatorResponse;

    public SongResponse(Song song, List<Creator> creator) {
        this.songId = song.getSongId();
        this.title = song.getTitle();
        this.link = song.getLink();
        this.imageUrl = song.getImageUrl();
        this.sequence = song.getSequence();
        this.isTitle = song.getIsTitle();
        this.lyrics = song.getLyrics();
        this.introduction = song.getIntroduction();
        this.albumId = song.getAlbum().getAlbumId();
        this.albumTitle = song.getAlbum().getTitle();
        this.releasedAt = song.getAlbum().getReleasedAt();
        this.creatorResponse = creator.stream().map(SongCreatorResponse::new).toList();
    }
}
