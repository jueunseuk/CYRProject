package com.junsu.cyr.service.song;

import com.junsu.cyr.constant.MagicNumberConstant;
import com.junsu.cyr.domain.songs.Album;
import com.junsu.cyr.domain.songs.Song;
import com.junsu.cyr.repository.SongRepository;
import com.junsu.cyr.response.exception.code.SongExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SongService {

    private final SongRepository songRepository;
    private final AlbumService albumService;

    @Transactional
    public Song createReleasedSong(Album album, String title, String link, Integer sequence, Boolean isTitle, String lyrics, String introduction) {
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
                introduction
        );
        return songRepository.save(song);
    }

    @Transactional
    public Song createUnreleasedSong(String title, String link, String lyrics, String introduction) {
        Song song = Song.of(
                albumService.getAlbumByAlbumId(MagicNumberConstant.UNRELEASED_ALBUM_ID),
                null,
                title,
                link,
                1,
                false,
                lyrics,
                introduction
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
        return songRepository.findAllByAlbumOrderBySequence(album);
    }

    public List<Song> getSongsByAlbumId(Integer albumId) {
        return getSongsByAlbum(albumService.getAlbumByAlbumId(albumId));
    }

    public List<Song> getAllSong() {
        return songRepository.findAllByHierarchyOrder();
    }
}
