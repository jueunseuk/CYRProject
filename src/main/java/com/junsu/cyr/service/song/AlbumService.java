package com.junsu.cyr.service.song;

import com.junsu.cyr.domain.songs.Album;
import com.junsu.cyr.repository.AlbumRepository;
import com.junsu.cyr.response.exception.code.AlbumExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlbumService {

    private final AlbumRepository albumRepository;

    @Transactional
    public Album createAlbum(String title, String imageUrl, LocalDate releasedAt, String introduction, String agency, String publisher) {
        if(title == null || title.isEmpty()) {
            throw new BaseException(AlbumExceptionCode.TOO_SHORT_TITLE);
        }
        if(imageUrl == null || imageUrl.isEmpty()) {
            throw new BaseException(AlbumExceptionCode.TOO_SHORT_IMAGE_URL);
        }
        if(releasedAt == null || releasedAt.isAfter(LocalDate.now().plusDays(7))) {
            throw new BaseException(AlbumExceptionCode.INCORRECT_RELEASE_DATE);
        }
        if(introduction == null || introduction.isEmpty()) {
            throw new BaseException(AlbumExceptionCode.TOO_SHORT_INTRODUCTION);
        }

        Album album = Album.builder()
                .title(title)
                .imageUrl(imageUrl)
                .releasedAt(releasedAt)
                .introduction(introduction)
                .agency(agency)
                .publisher(publisher)
                .build();

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

    public List<Album> getAlbumsByPeriod(LocalDate start, LocalDate end, Pageable pageable) {
        if(start == null || end == null) {
            throw new BaseException(AlbumExceptionCode.INVALID_SEARCH_CONDITION);
        }
        return albumRepository.findAllByReleasedAtBetween(start, end, pageable);
    }

    public List<Album> getAllAlbums() {
        return albumRepository.findAllByOrderByReleasedAt();
    }
}
