package com.junsu.cyr.model.song;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SongUploadRequest {
    private String title;
    private String link;
    private Integer songId;
    private Integer sequence;
    private Boolean isTitle;
    private String lyrics;
    private List<CreatorUploadRequest> songCreators;
}
