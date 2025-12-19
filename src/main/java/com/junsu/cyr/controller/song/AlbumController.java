package com.junsu.cyr.controller.song;

import com.junsu.cyr.domain.songs.Album;
import com.junsu.cyr.flow.song.ReleaseAlbumFlow;
import com.junsu.cyr.model.song.AlbumResponse;
import com.junsu.cyr.model.song.AlbumTotalResponse;
import com.junsu.cyr.model.song.AlbumUploadRequest;
import com.junsu.cyr.service.song.AlbumService;
import com.junsu.cyr.service.song.SongSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/album")
public class AlbumController {

    private final AlbumService albumService;
    private final ReleaseAlbumFlow releaseAlbumFlow;
    private final SongSearchService songSearchService;

    @PostMapping
    public ResponseEntity<String> uploadAlbum(@ModelAttribute AlbumUploadRequest request, @RequestAttribute Integer userId) {
        releaseAlbumFlow.releaseAlbum(request, userId);
        return ResponseEntity.ok("success to upload album");
    }

    @GetMapping("/all")
    public ResponseEntity<List<AlbumResponse>> getAllAlbums() {
        List<Album> albums = albumService.getAllAlbums();
        return ResponseEntity.ok(albums.stream().map(AlbumResponse::new).toList());
    }

    @GetMapping("/{albumId}")
    public ResponseEntity<AlbumTotalResponse> getAlbumDetail(@PathVariable Integer albumId, @RequestAttribute Integer userId) {
        AlbumTotalResponse albumTotalResponse = songSearchService.getTotalAlbumData(albumId, userId);
        return ResponseEntity.ok(albumTotalResponse);
    }
}
