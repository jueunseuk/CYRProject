package com.junsu.cyr.model.song;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlbumUploadRequest {
    private String title;
    private MultipartFile file;
    private String introduction;
    private String agency;
    private String publisher;
    private LocalDate releasedAt;
    private List<SongUploadRequest> songs;
}
