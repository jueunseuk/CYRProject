package com.junsu.cyr.model.post;

import com.junsu.cyr.domain.boards.Board;
import lombok.Data;

@Data
public class PostUploadResponse {
    private Integer boardId;
    private String boardName;
    private Long postId;

    public PostUploadResponse(Board board, Long postId) {
        this.boardId = board.getBoardId();
        this.boardName = board.getName();
        this.postId = postId;
    }
}
