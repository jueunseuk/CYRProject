package com.junsu.cyr.service.song;

import com.junsu.cyr.domain.songs.Album;
import com.junsu.cyr.domain.songs.Song;
import com.junsu.cyr.repository.SongRepository;
import com.junsu.cyr.response.exception.code.SongExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import com.junsu.cyr.util.PageableMaker;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SongService {

    private final SongRepository songRepository;

    @Transactional
    public Song createReleasedSong(Album album, String title, String link, Integer sequence, Boolean representative, String lyrics) {
        if(album == null) {
            throw new BaseException(SongExceptionCode.INVALID_ALBUM);
        }
        if(title == null || title.isEmpty()) {
            throw new BaseException(SongExceptionCode.TOO_SHORT_TITLE);
        }
        if(link == null && link.isEmpty()) {
            throw new BaseException(SongExceptionCode.TOO_SHORT_LINK);
        }
        if(sequence == null || sequence < 0) {
            throw new BaseException(SongExceptionCode.INVALID_SEQUENCE);
        }
        if(lyrics == null || lyrics.isEmpty()) {
            throw new BaseException(SongExceptionCode.TOO_SHORT_LYRICS);
        }

        Song song = Song.builder()
                .album(album)
                .imageUrl(album.getImageUrl())
                .title(title)
                .link(link)
                .isReleased(true)
                .sequence(sequence)
                .representative(false)
                .lyrics(lyrics)
                .build();

        songRepository.save(song);
        return song;
    }

    @Transactional
    public void createUnreleasedSong(String title, String link) {
        if(title == null || title.isEmpty()) {
            throw new BaseException(SongExceptionCode.TOO_SHORT_TITLE);
        }
        if(link == null && link.isEmpty()) {
            throw new BaseException(SongExceptionCode.TOO_SHORT_LINK);
        }

        Song song = Song.builder()
                .album(null)
                .imageUrl(null)
                .title(title)
                .link(link)
                .isReleased(false)
                .sequence(1)
                .representative(false)
                .lyrics(null)
                .build();

        songRepository.save(song);
    }

    public Song getSongBySongId(Integer songId) {
        return songRepository.findById(songId)
                .orElseThrow(() -> new BaseException(SongExceptionCode.NOT_FOUND_SONG_ID));
    }

    public List<Song> getSongsByIsReleased(Boolean isReleased, Pageable pageable) {
        return songRepository.findAllByIsReleased(isReleased, pageable);
    }

    public List<Song> getSongByTitle(String title) {
        return songRepository.findAllByTitle(title);
    }

    public List<Song> getSongsByAlbum(Album album) {
        Pageable pageable = PageableMaker.of("sequence", PageableMaker.ASC);
        return songRepository.findAllByAlbum(album, pageable);
    }

    public Page<Song> getAllSong(Pageable pageable) {
        return songRepository.findAll(pageable);
    }
}
