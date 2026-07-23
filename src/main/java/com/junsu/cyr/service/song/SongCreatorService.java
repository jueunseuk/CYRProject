package com.junsu.cyr.service.song;

import com.junsu.cyr.domain.songs.CreatorRole;
import com.junsu.cyr.domain.songs.Song;
import com.junsu.cyr.domain.songs.Creator;
import com.junsu.cyr.repository.SongCreatorRepository;
import com.junsu.cyr.response.exception.code.CreatorExceptionCode;
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
    public void createCreator(Song song, String name, CreatorRole creatorRole) {
        Creator creator = Creator.of(song, name, creatorRole);
        songCreatorRepository.save(creator);
    }

    public List<Creator> getSongCreatorsBySong(Song song) {
        if(song == null) {
            throw new BaseException(CreatorExceptionCode.CANNOT_MAPPING_TO_SONG);
        }
        return songCreatorRepository.findAllBySong(song);
    }

    public List<Creator> getSongCreatorsByName(String name) {
        if(name == null || name.isEmpty()) {
            throw new BaseException(CreatorExceptionCode.TOO_SHORT_NAME);
        }
        return songCreatorRepository.findAllByName(name);
    }

    public List<Creator> getSongCreatorsInSongId(List<Integer> songIds) {
        return songCreatorRepository.findAllBySong_SongIdIn(songIds);
    }
}
