package com.junsu.cyr.model.song;

import com.junsu.cyr.domain.songs.Creator;
import com.junsu.cyr.domain.songs.CreatorRole;
import lombok.Data;

@Data
public class SongCreatorResponse {
    private Integer creatorId;
    private String name;
    private CreatorRole creatorRole;

    public SongCreatorResponse(Creator creator) {
        this.creatorId = creator.getCreatorId();
        this.name = creator.getName();
        this.creatorRole = creator.getCreatorRole();
    }
}
