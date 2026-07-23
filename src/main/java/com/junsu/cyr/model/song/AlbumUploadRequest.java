package com.junsu.cyr.model.song;

import com.junsu.cyr.domain.songs.AlbumType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlbumUploadRequest {
    private String title;
    private MultipartFile file;
    private String introduction;
    private AlbumType albumType;
    private LocalDateTime releasedAt;
    private List<SongUploadRequest> songs;
}
