package com.junsu.cyr.service.song;

import com.junsu.cyr.domain.songs.Album;
import com.junsu.cyr.domain.songs.AlbumType;
import com.junsu.cyr.repository.AlbumRepository;
import com.junsu.cyr.response.exception.code.AlbumExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlbumService {

    private final AlbumRepository albumRepository;

    @Transactional
    public Album createAlbum(String title, String imageUrl, LocalDateTime releasedAt, String introduction, AlbumType type) {
        Album album = Album.of(title, imageUrl, releasedAt, introduction, type);
        return albumRepository.save(album);
    }

    public Album getAlbumByAlbumId(Integer albumId) {
        if(albumId == null || albumId <= 0) {
            throw new BaseException(AlbumExceptionCode.INVALID_SEARCH_CONDITION);
        }
        return albumRepository.findById(albumId)
                .orElseThrow(() -> new BaseException(AlbumExceptionCode.NOT_FOUND_ALBUM_ID));
    }

    public Album getAlbumByTitle(String title) {
        if(title == null || title.isEmpty()) {
            throw new BaseException(AlbumExceptionCode.INVALID_SEARCH_CONDITION);
        }
        return albumRepository.findByTitle(title);
    }

    public List<Album> getAlbumsByPeriod(LocalDateTime start, LocalDateTime end, Pageable pageable) {
        if(start == null || end == null) {
            throw new BaseException(AlbumExceptionCode.INVALID_SEARCH_CONDITION);
        }
        return albumRepository.findAllByReleasedAtBetween(start, end, pageable);
    }

    public List<Album> getAllAlbums() {
        return albumRepository.findAllByOrderByReleasedAt();
    }
}
