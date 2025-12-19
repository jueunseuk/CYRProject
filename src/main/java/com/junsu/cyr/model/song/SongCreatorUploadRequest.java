package com.junsu.cyr.model.song;

import com.junsu.cyr.domain.songs.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SongCreatorUploadRequest {
    private String name;
    private Type type;
}
