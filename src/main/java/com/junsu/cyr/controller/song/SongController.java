package com.junsu.cyr.controller.song;

import com.junsu.cyr.domain.songs.Song;
import com.junsu.cyr.model.song.SimpleSongResponse;
import com.junsu.cyr.model.song.SongResponse;
import com.junsu.cyr.service.song.SongSearchService;
import com.junsu.cyr.service.song.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/albums/{albumId}/songs")
public class SongController {

    private final SongService songService;
    private final SongSearchService songSearchService;

    @GetMapping
    public ResponseEntity<List<SimpleSongResponse>> getAllSongs(@PathVariable Integer albumId) {
        List<Song> songResponses = songService.getSongsByAlbumId(albumId);
        return ResponseEntity.ok(songResponses.stream().map(SimpleSongResponse::new).toList());
    }

    @GetMapping("/{songId}")
    public ResponseEntity<SongResponse> getAllSongs(@PathVariable Integer albumId, @PathVariable Integer songId) {
        SongResponse responses = songSearchService.getSongInformation(albumId, songId);
        return ResponseEntity.ok(responses);
    }
}
