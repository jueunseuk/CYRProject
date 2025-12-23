package com.junsu.cyr.controller.song;

import com.junsu.cyr.domain.songs.Song;
import com.junsu.cyr.model.song.SongResponse;
import com.junsu.cyr.model.song.SongSearchConditionRequest;
import com.junsu.cyr.service.song.SongService;
import com.junsu.cyr.util.PageableMaker;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/song")
public class SongController {

    private final SongService songService;

    @GetMapping("/all")
    public ResponseEntity<List<SongResponse>> getAllSongs(@RequestParam SongSearchConditionRequest condition) {
        Page<Song> songResponses = songService.getAllSong(PageableMaker.of(condition.getSort(), condition.getDirection()));
        return ResponseEntity.ok(songResponses.getContent().stream().map(SongResponse::new).toList());
    }
}
