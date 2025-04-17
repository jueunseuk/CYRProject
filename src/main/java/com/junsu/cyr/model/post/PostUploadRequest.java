package com.junsu.cyr.model.post;

import com.junsu.cyr.domain.boards.Board;
import com.junsu.cyr.domain.posts.Locked;
import lombok.Data;

@Data
public class PostUploadRequest {
    private String title;
    private String content;
    private Board board;
    private Locked locked;
}
