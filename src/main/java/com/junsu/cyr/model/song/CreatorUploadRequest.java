package com.junsu.cyr.model.song;

import com.junsu.cyr.domain.songs.CreatorRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatorUploadRequest {
    private String name;
    private CreatorRole creatorRole;
}
