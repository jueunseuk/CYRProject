package com.junsu.cyr.service.song;

import com.junsu.cyr.domain.songs.Album;
import com.junsu.cyr.domain.songs.Song;
import com.junsu.cyr.domain.songs.SongStatus;
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
    public Song createReleasedSong(Album album, String title, String link, Integer sequence, Boolean isTitle, String lyrics) {
        if(album == null) {
            throw new BaseException(SongExceptionCode.INVALID_ALBUM);
        }

        Song song = Song.of(
                album,
                album.getImageUrl(),
                title,
                link,
                sequence,
                isTitle,
                lyrics,
                SongStatus.RELEASED
        );
        return songRepository.save(song);
    }

    @Transactional
    public Song createUnreleasedSong(String title, String link, String lyrics) {
        Song song = Song.of(
                null,
                null,
                title,
                link,
                1,
                false,
                lyrics,
                SongStatus.UNRELEASED
        );
        return songRepository.save(song);
    }

    public Song getSongBySongId(Integer songId) {
        return songRepository.findById(songId)
                .orElseThrow(() -> new BaseException(SongExceptionCode.NOT_FOUND_SONG_ID));
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
