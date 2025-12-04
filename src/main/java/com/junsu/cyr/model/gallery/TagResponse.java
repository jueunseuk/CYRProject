package com.junsu.cyr.model.gallery;

import com.junsu.cyr.domain.gallery.Tag;
import lombok.Data;

@Data
public class TagResponse {
    private Integer tagId;
    private String name;

    public TagResponse(Tag tag) {
        this.tagId = tag.getTagId();
        this.name = tag.getName();
    }
}
