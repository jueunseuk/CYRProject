package com.junsu.cyr.model.song;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnreleasedSongUploadRequest {
    private String title;
    private String lyrics;
}
