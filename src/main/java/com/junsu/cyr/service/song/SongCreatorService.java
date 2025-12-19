package com.junsu.cyr.service.song;

import com.junsu.cyr.domain.songs.Song;
import com.junsu.cyr.domain.songs.SongCreator;
import com.junsu.cyr.domain.songs.Type;
import com.junsu.cyr.repository.SongCreatorRepository;
import com.junsu.cyr.response.exception.code.SongCreatorExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SongCreatorService {

    private final SongCreatorRepository songCreatorRepository;

    @Transactional
    public void createSongCreator(Song song, String name, Type type) {
        if(song == null) {
            throw new BaseException(SongCreatorExceptionCode.CANNOT_MAPPING_TO_SONG);
        }
        if(name == null || name.isEmpty()) {
            throw new BaseException(SongCreatorExceptionCode.TOO_SHORT_NAME);
        }
        if(type == null) {
            throw new BaseException(SongCreatorExceptionCode.INVALID_SONG_CREATOR_TYPE);
        }

        SongCreator songCreator = SongCreator.builder().song(song).name(name).type(type).build();
        songCreatorRepository.save(songCreator);
    }

    public List<SongCreator> getSongCreatorsBySong(Song song) {
        if(song == null) {
            throw new BaseException(SongCreatorExceptionCode.CANNOT_MAPPING_TO_SONG);
        }
        return songCreatorRepository.findAllBySong(song);
    }

    public List<SongCreator> getSongCreatorsByName(String name) {
        if(name == null || name.isEmpty()) {
            throw new BaseException(SongCreatorExceptionCode.TOO_SHORT_NAME);
        }
        return songCreatorRepository.findAllByName(name);
    }

    public List<SongCreator> getSongCreatorsInSongId(List<Integer> songIds) {
        return songCreatorRepository.findAllBySong_SongIdIn(songIds);
    }
}
