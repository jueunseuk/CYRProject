package com.junsu.cyr.domain.songs;

import com.junsu.cyr.response.exception.code.SongExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "song")
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "song_id")
    private Integer songId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album")
    private Album album;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "link")
    private String link;

    @Column(name = "is_released")
    private Boolean isReleased;

    @Column(name = "sequence", nullable = false)
    private Integer sequence;

    @Column(name = "representative")
    private Boolean representative;

    @Column(name = "lyrics")
    private String lyrics;

    public void updateTitle(String title) {
        if(title == null || title.isEmpty()) {
            throw new BaseException(SongExceptionCode.TOO_SHORT_TITLE);
        }
        this.title = title;
    }

    public void updateRepresentative(Boolean representative) {
        this.representative = representative;
    }

    public void updateLink(String link) {
        if(link == null || link.isEmpty()) {
            throw new BaseException(SongExceptionCode.TOO_SHORT_LINK);
        }
        this.link = link;
    }

    public void updateRelease() {
        this.isReleased = true;
    }

    public void updateSequence(Integer sequence) {
        if(sequence == null || sequence < 1) {
            throw new BaseException(SongExceptionCode.INVALID_SEQUENCE);
        }
        this.sequence = sequence;
    }

    public void updateLyrics(String lyrics) {
        if(lyrics == null || lyrics.isEmpty()) {
            throw new BaseException(SongExceptionCode.TOO_SHORT_LYRICS);
        }
        this.lyrics = lyrics;
    }
}
