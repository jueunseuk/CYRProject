package com.junsu.cyr.service.song;

import com.junsu.cyr.domain.songs.Album;
import com.junsu.cyr.domain.songs.Song;
import com.junsu.cyr.domain.songs.Creator;
import com.junsu.cyr.model.song.AlbumTotalResponse;
import com.junsu.cyr.model.song.SongTotalResponse;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SongSearchService {

    private final UserService userService;
    private final AlbumService albumService;
    private final SongService songService;
    private final SongCreatorService songCreatorService;

    public AlbumTotalResponse getTotalAlbumData(Integer albumId, Integer userId) {
        userService.getUserById(userId);

        AlbumTotalResponse albumTotalResponse = new AlbumTotalResponse();

        Album album = albumService.getAlbumByAlbumId(albumId);
        albumTotalResponse.saveAlbum(album);

        List<Song> songs = songService.getSongsByAlbum(album);
        List<Integer> songIds = songs.stream().map(s -> s.getSongId()).collect(Collectors.toList());

        List<Creator> creators = songCreatorService.getSongCreatorsInSongId(songIds);

        List<SongTotalResponse> songTotalResponses = new ArrayList<>();
        for(Song song : songs) {
            List<Creator> songCreator = creators.stream().filter(s -> song.equals(s.getSong())).collect(Collectors.toList());
            SongTotalResponse songTotalResponse = new SongTotalResponse();
            songTotalResponse.saveSong(song);
            songTotalResponse.saveSongCreator(songCreator);
            songTotalResponses.add(songTotalResponse);
        }

        albumTotalResponse.saveSong(songTotalResponses);
        return albumTotalResponse;
    }
}
